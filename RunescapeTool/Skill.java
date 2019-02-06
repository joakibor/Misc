
public class Skill {
	private int xp, rank, level;
	private String name;
	
	public Skill(int i, int i_rank, int i_level, int i_xp){
		xp = i_xp;
		rank = i_rank;
		level = i_level;
		switch(i){
		case 1: name = "Attack";
			break;
		case 2: name = "Defence";
		break;
		case 3: name = "Strength";
		break;
		case 4: name = "Constitution";
		break;
		case 5: name = "Ranged";
		break;
		case 6: name = "Prayer";
		break;
		case 7: name = "Magic";
		break;
		case 8: name = "Cooking";
		break;
		case 9: name = "Woodcutting";
		break;
		case 10: name = "Fletching";
		break;
		case 11: name = "Fishing";
		break;
		case 12: name = "Firemaking";
		break;
		case 13: name = "Crafting";
		break;
		case 14: name = "Smithing";
		break;
		case 15: name = "Mining";
		break;
		case 16: name = "Herblore";
		break;
		case 17: name = "Agility";
		break;
		case 18: name = "Thieving";
		break;
		case 19: name = "Slayer";
		break;
		case 20: name = "Farming";
		break;
		case 21: name = "Runecrafting";
		break;
		case 22: name = "Hunter";
		break;
		case 23: name = "Construction";
		break;
		case 24: name = "Summoning";
		break;
		case 25: name = "Dungeoneering";
		break;
		case 26: name = "Divination";
		break;
		case 27: name = "Invention";
		break;
		}
	}
	
	public int getRank(){
		return rank;
	}
	
	public int getLevel(){
		return level;
	}

	public int getXp(){
		return xp;
	}
	
	public String getName(){
		return name;
	}
	

	public String prettyPrint(){
		return name + " level: " + level + " total xp: " + xp + " Rank: " + rank;
	}
	
	public String saveString(){
		return " " + rank +"," + level + "," +xp;
	}

}
