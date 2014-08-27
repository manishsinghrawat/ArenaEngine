import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;


public class heighttotile {

	public static void main(String[] args) {
		int i,j,min,tileid;
		String a1,a2,a3,a4;
		int size=64;
		Tile tile;
		String str="";
		Random rand=new Random(System.currentTimeMillis());
		try {
			File file=new File("c:/MAP.TXT");
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			str = new String(chars);
			reader.close();


			String[] splited = str.split("[^a-zA-Z0-9-]+");
			
			String out="";
		//	out+=ln(splited.length);
			for(j=0;j<size;j++){
				a1=splited[(size+1)*j];
				a2=splited[(size+1)*(j+1)];
				for(i=0;i<size;i++){
					a3=splited[(size+1)*j+i+1];
					a4=splited[(size+1)*(j+1)+i+1];
					//find minimum terrain height across tile
					
					float number=rand.nextFloat();
					tile = gettile(a1,a2,a3,a4);
					//out+=ln(tile.toString());
					out+=("{");
					if(tile.compare("0","0","0","0")){
						if(number<0.5)
							out+=("\"modelid\": 0");
						else if(number<0.8)
							out+=("\"modelid\": 0");
						else
							out+=("\"modelid\": 0");
					}
					if(tile.compare("1","0","0","0")){
						out+=("\"modelid\": 1");
					}
					if(tile.compare("0","1","0","0")){
						out+=("\"modelid\": 2");
					}
					if(tile.compare("0","0","1","0")){
						out+=("\"modelid\": 3");
					}
					if(tile.compare("0","0","0","1")){
						out+=("\"modelid\": 4");
					}
					if(tile.compare("1","0","0","1")){
						out+=("\"modelid\": 5");
					}
					if(tile.compare("0","1","1","0")){
						out+=("\"modelid\": 6");
					}
					if(tile.compare("1","1","0","0")){
						out+=("\"modelid\": 7");
					}
					if(tile.compare("0","0","1","1")){
						out+=("\"modelid\": 8");
					}
					if(tile.compare("1","0","1","0")){
						out+=("\"modelid\": 9");
					}
					if(tile.compare("0","1","0","1")){
						out+=("\"modelid\": 10");
					}
					if(tile.compare("0","1","1","1")){
						out+=("\"modelid\": 11");
					}
					if(tile.compare("1","0","1","1")){
						out+=("\"modelid\": 12");
					}
					if(tile.compare("1","1","0","1")){
						out+=("\"modelid\": 13");
					}
					if(tile.compare("1","1","1","0")){
						out+=("\"modelid\": 14");
					}
					
					if(tile.compare("0","0","R","R")){
						if(Integer.parseInt(splited[(size+1)*j+i+2])<tile.minheight){
							tile.minheight-=1;
							out+=("\"modelid\": 19");
						}
						else
							out+=("\"modelid\": 15");
					}
					if(tile.compare("R","R","0","0")){
						System.out.println(Integer.parseInt(splited[(size+1)*j+i-1])<tile.minheight);
						if(Integer.parseInt(splited[(size+1)*j+i-1])<tile.minheight){
							tile.minheight-=1;
							out+=("\"modelid\": 20");
						}
						else
							out+=("\"modelid\": 16");
					}
					if(tile.compare("R","0","R","0")){
						if(Integer.parseInt(splited[(size+1)*(j-1)+i])<tile.minheight){
							tile.minheight-=1;
							out+=("\"modelid\": 21");
						}
						else
							out+=("\"modelid\": 17");
					}
					if(tile.compare("0","R","0","R")){
						if(Integer.parseInt(splited[(size+1)*(j+2)+i])<tile.minheight){
							tile.minheight-=1;
							out+=("\"modelid\": 22");
						}
						else
							out+=("\"modelid\": 18");
					}
				/*	if(tile.compare("1","1","R","R")){
						out+=("\"modelid\": 19");
					}
					if(tile.compare("R","R","1","1")){
						out+=("\"modelid\": 20");
					}
					if(tile.compare("R","1","R","1")){
						out+=("\"modelid\": 21");
					}
					if(tile.compare("1","R","1","R")){
						out+=("\"modelid\": 22");
					}
					*/
					if(tile.compare("R","0","0","0")){
						if(splited[(size+1)*(j-1)+i].equals("R")){
							out+=("\"modelid\": 23");
						}
						else{
							out+=("\"modelid\": 24");
						}
					}
					if(tile.compare("0","R","0","0")){
						if(splited[(size+1)*(j+2)+i].equals("R")){
							out+=("\"modelid\": 25");
						}
						else{
							out+=("\"modelid\": 26");
						}
					}
					if(tile.compare("0","0","R","0")){
						if(splited[(size+1)*(j-1)+i+1].equals("R")){
							out+=("\"modelid\": 27");
						}
						else{
							out+=("\"modelid\": 28");
						}
					}
					if(tile.compare("0","0","0","R")){
						if(splited[(size+1)*(j+2)+i+1].equals("R")){
							out+=("\"modelid\": 29");
						}
						else{
							out+=("\"modelid\": 30");
						}
					}
					
					if(tile.compare("R","0","1","1")){
						out+=("\"modelid\": 31");
					}
					if(tile.compare("R","1","0","1")){
						out+=("\"modelid\": 32");
					}
					if(tile.compare("1","R","1","0")){
						out+=("\"modelid\": 33");
					}
					if(tile.compare("0","R","1","1")){
						out+=("\"modelid\": 34");
					}
					if(tile.compare("0","1","R","1")){
						out+=("\"modelid\": 35");
					}
					if(tile.compare("1","1","R","0")){
						out+=("\"modelid\": 36");
					}
					if(tile.compare("1","1","0","R")){
						out+=("\"modelid\": 37");
					}
					if(tile.compare("1","0","1","R")){
						out+=("\"modelid\": 38");
					}
					
				
					
					if(tile.compare("R","1","0","0")){
						out+=("\"modelid\": 39");
					}
					if(tile.compare("R","0","1","0")){
						out+=("\"modelid\": 40");
					}
					if(tile.compare("1","R","0","0")){
						out+=("\"modelid\": 41");
					}
					if(tile.compare("0","R","0","1")){
						out+=("\"modelid\": 42");
					}
					if(tile.compare("1","0","R","0")){
						out+=("\"modelid\": 43");
					}
					if(tile.compare("0","0","R","1")){
						out+=("\"modelid\": 44");
					}
					if(tile.compare("0","1","0","R")){
						out+=("\"modelid\": 45");
					}
					if(tile.compare("0","0","1","R")){
						out+=("\"modelid\": 46");
					}
					
					out+=(",\"height\": "+tile.minheight+"},");
					a1=a3;
					a2=a4;
				}
				out+="\n";
			}
			PrintWriter output = new PrintWriter("c:/Mapout.txt");
			output.println(out);
			output.close();
		} catch(Exception ex){
			System.out.println(ex.toString());
		}
	}
	public static Tile gettile(String a1,String a2,String a3,String a4){
		Tile result=new Tile();
		String[] ret=new String[4];
		int min=1000;
		if(isNumeric(a1)){
			if(min>Integer.parseInt(a1))
				min=Integer.parseInt(a1);
		}
		if(isNumeric(a2)){
			if(min>Integer.parseInt(a2))
				min=Integer.parseInt(a2);
		}
		if(isNumeric(a3)){
			if(min>Integer.parseInt(a3))
				min=Integer.parseInt(a3);
		}		
		if(isNumeric(a4)){
			if(min>Integer.parseInt(a4))
				min=Integer.parseInt(a4);
		}
		result.minheight=min;
		if(isNumeric(a1))
			ret[0]=Integer.toString(Integer.parseInt(a1)-min);
		else
			ret[0]=a1;
		if(isNumeric(a2))
			ret[1]=Integer.toString(Integer.parseInt(a2)-min);
		else
			ret[1]=a2;
		if(isNumeric(a3))
			ret[2]=Integer.toString(Integer.parseInt(a3)-min);
		else
			ret[2]=a3;
		if(isNumeric(a4))
			ret[3]=Integer.toString(Integer.parseInt(a4)-min);
		else
			ret[3]=a4;
		
		result.tileid=ret;
		
		return result;
	}
	public static int equal(int a1,int a2,int a3,int a4){
		return (a1)+(a2)*2+(a3)*4+(a4)*8;
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
