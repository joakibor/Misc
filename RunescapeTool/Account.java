import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class Account {
	private String name;
	private int rank, totalLvl;
	private long totalXp;
	private Skill[] skills = null;
	
	
	public Account(String i_name){
		name = i_name;
		name = name.replace(' ', '_');	
		setupAcc();
	}
	
	public String save(){
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy,HH:mm:ss");
		Date dateobj = new Date();
		String retString = name + "," + df.format(dateobj).toString() +" "+ rank +"," + totalLvl + "," + totalXp;
		
		for(Skill s : skills){
			retString += s.saveString();
		}
		return retString;
	}
	
	private void setupAcc(){
		String result = getAccount();
		char[] arr = result.toCharArray();
		skills = new Skill[27];
		
		String[] lst = result.split(" ");

		int teller = 1;
		boolean readFirst = true;
		
		for(String s: lst){
			if(teller >= 28)
				break;
			String[] i_lst = s.split(",");
			
			if(readFirst){				
				for(String b : i_lst){
					rank = Integer.parseInt(i_lst[0]);
					totalLvl = Integer.parseInt(i_lst[1]);
					totalXp = Long.parseLong(i_lst[2]);
				}
				readFirst = false;
			}
			else{
				
				skills[teller-1] = new Skill(teller, Integer.parseInt(i_lst[0]),
													   Integer.parseInt(i_lst[1]),
													   Integer.parseInt(i_lst[2]));	
				teller++;
			}
			
		}
	}

	public String getSkill(String name){
		for(Skill s : skills){
			if(s.getName().equalsIgnoreCase(name))
				return s.toString();				
		}
		return "No such skill as " + name;
	}
	
	private String getAccount(){
		Document doc = null;
		String search = "https://secure.runescape.com/m=hiscore/index_lite.ws?player=";
		search+= name;
		try{		
		doc = Jsoup.connect(search).get();
		}catch(Exception ex){
			System.out.println("invalid account name");
			System.exit(1);
		}
		String res = Jsoup.clean(doc.body().html(), Whitelist.basic());	
		return res;
	}

}
