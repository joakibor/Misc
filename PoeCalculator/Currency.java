package testBUilder;

public class Currency {
	
	private String name = "";
	private Double value;
	private int antall;
	private int idx;
	public Currency(String i_name, Double i_value){
		name = i_name;
		value = i_value;
	}
	
	public Currency(String i_name, int i_antall){
		name = i_name;
		antall = i_antall;
	}
	
	public int getIdx(){
		return idx;
	}
	
	public void setIdx(int i_idx){
		idx = i_idx;
	}
	
	public String getName(){
		return name;
	}
	
	public Double getValue(){
		return value;
	}
	
	public void setAntall(int i_antall){
		antall = i_antall;
	}
	public int getAntall(){
		return antall;
	}
	public String toText(){
		return(name + "," + antall);
	}

}
