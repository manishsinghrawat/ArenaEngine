
public class Tile {
	String[] tileid;
	int minheight;
	public boolean compare(String string, String string2, String string3,String string4) {
		if(string.equals(tileid[0]) && string2.equals(tileid[1]) && string3.equals(tileid[2]) && string4.equals(tileid[3]))
			return true;
		else
			return false;
		
	}
	public String toString(){
		String ret="";
		for(int i=0;i<4;i++)
			ret+=tileid[i]+",";
		return ret;
	}
}
