import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;


public class doodads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str;
		int size=64;
		try {
			File file=new File("/home/puneet/tree.txt");
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			str = new String(chars);
			reader.close();
			Random rand=new Random(System.currentTimeMillis());

			String[] splited = str.split("[^a-zA-Z0-9-]+");
			String out="";
			int i,j;
			for(j=0;j<size*2;j++){
				for(i=0;i<size*2;i++){
					//fix this when set to 128
					if(splited[j*(size*2+1)+i].equals("1")){
						
						out+="{\"x\": "+i+",\"y\": "+j+",\"modelid\": "+0+",\"rotation\": "+(int)(rand.nextFloat()*360)+ "},\n";
					}
				}
			}
			
			PrintWriter output = new PrintWriter("/home/puneet/doodadout.txt");
			output.println(out);
			output.close();
		} catch(Exception ex){
			System.out.println(ex.toString());
		}
	}

}
