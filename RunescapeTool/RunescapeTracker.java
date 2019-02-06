import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class RunescapeTracker {
	public static void main(String[] args) throws IOException{
		Account acc = searchAccount("TheUnseen");
		saveAccount(acc);

	}
	
	/*
	 * Read account back from file.
	 */
	public void readAccount(String name){
		
	}
	
	/*
	 * Write account to file.
	 */
	public static void saveAccount(Account acc) throws IOException{
		
		List<String> lines = Files.readAllLines(Paths.get("Saves.txt"), StandardCharsets.UTF_8);
		PrintWriter out;
		try {
			String retLine = "";
			for(String s : lines)
				retLine += s+"\n";
			out = new PrintWriter("Saves.txt");
			out.println(retLine + acc.save());
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("You done fukd up");
			e.printStackTrace();
		}		
		System.out.println("Saved!");
	}
	
	/*
	 * Searches the Jagex Runescape account api and gets all the stats for the account
	 */
	public static Account searchAccount(String name){
		Account acc = new Account(name);
		return acc;
	}
	
	/*
	 * Time in Runescape is tracked by RuneDate.
	 */
	public int getRuneDate() throws IOException{
		Document doc = Jsoup.connect("https://secure.runescape.com/m=itemdb_rs/api/info.json").get();
		String res = Jsoup.clean(doc.body().html(), Whitelist.basic());
		res = res.replaceAll("&quot;", "");
		String temp = "";
		for(char c : res.toCharArray()){
			if(Character.isDigit(c))
				temp += c;
		}
		res = temp;
		return (int) Integer.parseInt(res);
	}
}
