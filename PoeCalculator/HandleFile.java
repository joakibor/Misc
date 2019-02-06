package testBUilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HandleFile {
	
	public HandleFile(){

	}
	
	public ArrayList<Currency> lesFil() throws IOException{
		ArrayList<Currency> arr = new ArrayList<Currency>();
		File f = new File("PoECurrency.txt");
		if(f.exists()){
			BufferedReader  a = new BufferedReader(new FileReader(f));
			String line = a.readLine();
			int current = 0;
			if(line == null){
				a.close();
				return null;
			}
			while(line.contains(",") ){
				String[] parts = line.split(",");
				int idx = Integer.parseInt(parts[2]);
				arr.add(new Currency(parts[0], Integer.parseInt(parts[1])));
				arr.get(current).setIdx(idx);
				current++;
				line = a.readLine();
			}
			a.close();
			return arr;	
		}
		else{
			return null;
		}
	}
	
	
	public void SkrivFil(ArrayList<Currency> lst, Double total) throws IOException{
		File f = new File("PoECurrency.txt");
		
		
		f.createNewFile();
		String retString = "";
		for(int i = 0; i < lst.size(); i++){
			if(lst.get(i).getAntall() > 0){
				retString += lst.get(i).toText()+"," + i +"\n";
			}			
		}
		retString += "Last total chaos value: " + total + " \n--------------------\n";
		FileOutputStream output = new FileOutputStream(f);
		output.write(retString.getBytes());
		output.close();
	}
	
	
}
