package testBUilder;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JTextPane;

import org.jsoup.Jsoup;


import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class PoeCalc {
	
	public static void main(String[] args) throws IOException {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paintFrame();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
	}
	
	public PoeCalc() throws IOException {
			
	}
	
	public static void paintFrame() throws IOException{
		PoeCalc window = new PoeCalc();
		JFrame frame = window.createPanel();
		frame.setVisible(true);				
	}
	
	
	/*
	 * Creates the entire panel with all the buttons etc
	 * Should probably be split into several methods instead of its current length
	 */
	public JFrame createPanel() throws IOException{
		ArrayList<Currency> arr = getPrices();
		Collections.reverse(arr);
		JFrame frame;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		if(arr.size() == 0){
			System.exit(1);
		}
		
		JTextField[] lstMath = new JTextField[arr.size()];
		int teller = 0, x = 30, y = 30;
		
		for(int i = 0; i < lstMath.length; i++){
			
			JLabel lbl = new JLabel(arr.get(i).getName());
			lbl.setBounds(x, y, 140, 20);
			frame.getContentPane().add(lbl);
			lbl.setFont(new Font("Arial", Font.BOLD, 15));
			
			JTextField b = new JTextField();
			b.setBounds(x+145, y, 70, 20);
			b.setFont(new Font("Arial", Font.BOLD, 15));
			frame.getContentPane().add(b);
			lstMath[i] = b;
			
			x += 280;
			teller++;
			if((int) (teller % 4) == 0 && teller > 1){
				x = 30;
				y += 30;
			}
		}
		
		JTextPane result = new JTextPane();		
		result.setFont(new Font("Arial", Font.BOLD, 25));
		result.setEditable(false);
		result.setBounds(730, 500, 200, 100);
		frame.getContentPane().add(result);
		
		JButton calcBtn;
		calcBtn = new JButton("Enter!");		
		frame.getRootPane().setDefaultButton(calcBtn);
		calcBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double res = calculateResult(lstMath, arr, frame);
				result.setText(""+ res + "c");
			}
		});
		calcBtn.setBounds(620, 500, 100, 100);
		frame.getContentPane().add(calcBtn);
		
		
		JButton resetBtn = new JButton("Reset!");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for(JTextField f : lstMath){
					f.setText("");
					result.setText("");
				}
			}
		});
		resetBtn.setBounds(510, 500, 100, 100);
		frame.getContentPane().add(resetBtn);
		
		
		JButton saveBtn = new JButton("Save!");
		frame.getRootPane().setDefaultButton(saveBtn);
		
		double res = 0.0;
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(res != 0 && arr.size() > 0 ){
					System.out.println(res);
					try {
						WriteToFile(arr, res, frame);
					} catch (IOException e1) {
						System.exit(1);
						e1.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(frame, "There is nothing to save!");
			}
		});		
		saveBtn.setBounds(400, 500, 100, 100);
		frame.getContentPane().add(saveBtn);
		
		JButton getFileBtn = new JButton("Get from file");		
		frame.getRootPane().setDefaultButton(getFileBtn);
		getFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						ArrayList<Currency> ara = readFromFile();
						if(ara != null)
							for(Currency c : ara){
								lstMath[c.getIdx()].setText(""+c.getAntall());
							}	
						else{
							JOptionPane.showMessageDialog(frame, "You haven't saved anything you dimwit!"
									+ "\nEither that or this is bugged. In which case, my bad.");											
						}
					}
					 catch (IOException e1) {
						System.exit(1);
						e1.printStackTrace();
					}
			}
		});
		getFileBtn.setBounds(250, 500, 130, 100);
		frame.getContentPane().add(getFileBtn);
		
		JButton infoBtn = new JButton("How do I use this?");
		infoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				String teach = "Enter all the currency you want to price check and press enter to see how much it's worth.\n"
						+ "After you have done this at least once this session you can \"Save\" the result to File by pressing the Save key\n"
						+ "When you have saved your result to file you can press get file at a later point to retrieve those numbers.\n"
						+ "As of now you can only save one result so be careful about overwriting.\n"
						+ "Reset simply clears the board of all numbers.\n"
						+ "Get current prices gives you all the updates selling prices from poe.ninja.\n";
						
						
				JOptionPane.showMessageDialog(frame, teach);											

			}
		});
		infoBtn.setBounds(950, 500, 150, 100);
		frame.getContentPane().add(infoBtn);
		
		JButton priceBtn = new JButton("Get current prices");
		priceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				String msg = "";
				for(Currency c : arr){
					msg += c.getName() + ": " + c.getValue()+"c"+"\n";
				}	
				JOptionPane.showMessageDialog(frame, msg);	
			}
		});
		priceBtn.setBounds(90, 500, 150, 100);
		frame.getContentPane().add(priceBtn);
		return frame;
	}
	
	/*
	 * Gets all the current prices of the currency from the poe.ninja api.
	 */
	public ArrayList<Currency> getPrices() throws IOException{
		
		org.jsoup.nodes.Document doc = Jsoup.connect("https://poe.ninja/api/Data/GetCurrencyOverview?league=Betrayal").ignoreContentType(true).get(); 
		String val = "\"chaosEquivalent\":(\\d+\\.\\d+)";
		String name= "\"currencyTypeName\":\"(.+?)\"";
		Pattern r = Pattern.compile(val);
		Pattern r2 = Pattern.compile(name);
		Matcher m = r.matcher(doc.toString());
		Matcher m2 = r2.matcher(doc.toString());
		
		ArrayList<Currency> arr = new ArrayList<Currency>();

		boolean chaos = false;
		while(m.find()){		
			m2.find();
			double value = Double.parseDouble(m.group(1));
			if(value < 1 && chaos == false){
				arr.add(new Currency("Chaos", 1.0));
				chaos = true;
			}
			arr.add(new Currency(m2.group(1), value));
	    }
		return arr;
	}
	
	/*
	 * Saves the current total value to file for easy access later.
	 */
	public void WriteToFile(ArrayList<Currency> arr, Double res, JFrame frame) throws IOException{
		if(res < 0)
			JOptionPane.showMessageDialog(frame, "Please calculate an ammount first!");
		else{
			HandleFile a = new HandleFile();
			JOptionPane.showMessageDialog(frame, "Result has been saved!");
			a.SkrivFil(arr, res);
		}
	}
	
	/*
	 * Gets the last saved data.
	 */
	public ArrayList<Currency> readFromFile() throws IOException{
		HandleFile a = new HandleFile();
		return a.lesFil();
		
	}

	public Double calculateResult(JTextField[] math, ArrayList<Currency> curr, JFrame frame){
		
		Double result = 0.0;
		for(int i = 0; i < math.length; i++){
			if(notEmpty(math[i])){
				int antall = 0;
				try{
					antall = Integer.parseInt(math[i].getText());
				} catch(NumberFormatException e){
					JOptionPane.showMessageDialog(frame,math[i].getText() + " er ikke et tall.");		
				}
				result+= antall * curr.get(i).getValue();
				curr.get(i).setAntall(antall);
			}
		}
		return result;
	}
	
	public static boolean notEmpty(JTextField a){
		
		if(a.getText().isEmpty())
			return false;
		return true;
	}
}
