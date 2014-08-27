import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;


public class converter {

	public static void main(String[] args) {
		int i,j;

		int a1,a2,a3,a4;
		String b1,b2,b3,b4;
		int size=64;
		Random rand=new Random(System.currentTimeMillis());
		String str;
		try {
			File file1=new File("c:/terrain.txt");
			FileReader reader1 = new FileReader(file1);
			char[] chars1 = new char[(int) file1.length()];
			reader1.read(chars1);
			str = new String(chars1);
			reader1.close();
			String[] split1 = str.split("[^a-zA-Z0-9-]+");
			
			File file2=new File("c:/grass.txt");
			FileReader reader2 = new FileReader(file2);
			char[] chars2 = new char[(int) file2.length()];
			reader2.read(chars2);
			str = new String(chars2);
			reader2.close();
			String[] split2 = str.split("[^a-zA-Z0-9-]+");
			
			float number;
			int height;
			String out="";
			for(j=0;j<size;j++){
				a1=Integer.parseInt(split1[(size+1)*j]);
				a2=Integer.parseInt(split1[(size+1)*(j+1)]);
				b1=split2[(size+1)*j];
				b2=split2[(size+1)*(j+1)];
				
				for(i=0;i<size;i++){
					a3=Integer.parseInt(split1[(size+1)*j+i+1]);
					a4=Integer.parseInt(split1[(size+1)*(j+1)+i+1]);
					b3=split2[(size+1)*j+i+1];
					b4=split2[(size+1)*(j+1)+i+1];

					number=rand.nextFloat();

					out+=("{");
					
					height=minheight(a1,a2,a3,a4);
					
					if(compare(0,0,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4)){
							if(number<=0.5)	
								out+=("\"modelid\": 0");
							else if(number<=0.85)
								out+=("\"modelid\": 1");
							else
								if(number<=0.89)
									out+=("\"modelid\": 2");
								else if(number>=0.90&&number<=93)
									out+=("\"modelid\":114");
								else if(number>=0.94&&number<=97)
									out+=("\"modelid\":115");
								else
									out+=("\"modelid\":116");
						}
						else if(compare("G","G","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 3");
						else if(compare("G","G","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 4");
						else if(compare("G","D","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 5");
						else if(compare("D","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 6");
						else if(compare("G","G","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 7");
						else if(compare("D","G","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 8");
						else if(compare("D","D","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 9");
						else if(compare("G","D","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 10");
						else if(compare("G","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 11");
						else if(compare("D","G","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 12");
						else if(compare("D","D","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 13");
						else if(compare("D","D","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 14");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 15");
					}
					else if(compare(0,1,0,1,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 16");
						else if(compare("G","G","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 17");
						else if(compare("D","D","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 18");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 19");
					}
					else if(compare(0,0,1,1,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 20");
						else if(compare("D","G","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 21");
						else if(compare("G","D","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 22");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 23");
					}
					else if(compare(1,0,1,0,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 24");
						else if(compare("G","G","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 25");
						else if(compare("D","D","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 26");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 27");
					}
					else if(compare(1,1,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 28");
						else if(compare("D","G","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 29");
						else if(compare("G","D","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 30");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 31");
					}
					else if(compare(1,2,1,2,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 32");
						else if(compare("G","G","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 33");
						else if(compare("D","D","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 34");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 35");
					}else if(compare(1,1,2,2,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 36");
						else if(compare("D","G","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 37");
						else if(compare("G","D","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 38");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 39");
					}else if(compare(2,1,2,1,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 40");
						else if(compare("G","G","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 41");
						else if(compare("D","D","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 42");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 43");
					}
					else if(compare(2,2,1,1,a1-height,a2-height,a3-height,a4-height)){
						if(compare("G","G","G","G",b1,b2,b3,b4))
							out+=("\"modelid\": 44");
						else if(compare("D","G","D","G",b1,b2,b3,b4))
							out+=("\"modelid\": 45");
						else if(compare("G","D","G","D",b1,b2,b3,b4))
							out+=("\"modelid\": 46");
						else if(compare("D","D","D","D",b1,b2,b3,b4))
							out+=("\"modelid\": 47");
					}
					else if(compare(2,2,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 48");
						else
							out+=("\"modelid\": 49");
					}
					else if(compare(0,0,2,2,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 50");
						else
							out+=("\"modelid\": 51");
					}else if(compare(2,0,2,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 52");
						else
							out+=("\"modelid\": 53");
					}else if(compare(0,2,0,2,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 54");
						else
							out+=("\"modelid\": 55");
					}else if(compare(0,0,2,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 56");
						else
							out+=("\"modelid\": 57");
					}else if(compare(0,0,0,2,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 58");
						else
							out+=("\"modelid\": 59");
					}
					else if(compare(2,0,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 60");
						else
							out+=("\"modelid\": 61");
					}else if(compare(0,2,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 62");
						else
							out+=("\"modelid\": 63");
					}else if(compare(1,2,0,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 64");
					}else if(compare(0,1,2,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 65");
					}else if(compare(2,2,1,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 66");
					}else if(compare(2,0,2,1,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 67");
					}else if(compare(0,0,0,1,a1-height,a2-height,a3-height,a4-height)){
						if(Integer.parseInt(split1[(size+1)*(j+1)+(i+2)])%2==0)
							out+=("\"modelid\": 68");
						else
							out+=("\"modelid\": 69");
					}
					else if(compare(0,0,1,0,a1-height,a2-height,a3-height,a4-height)){
						if(Integer.parseInt(split1[(size+1)*(j)+(i+2)])%2==0)
							out+=("\"modelid\": 70");
						else
							out+=("\"modelid\": 71");
					}
					else if(compare(0,1,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(Integer.parseInt(split1[(size+1)*(j+1)+(i-1)])%2==0)
							out+=("\"modelid\": 72");
						else
							out+=("\"modelid\": 73");
					}
					else if(compare(1,0,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(Integer.parseInt(split1[(size+1)*(j)+(i-1)])%2==0)
							out+=("\"modelid\": 74");
						else
							out+=("\"modelid\": 75");
					}
					else if(compare(0,2,1,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 76");
					}else if(compare(1,0,2,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 77");
					}else if(compare(2,2,0,1,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 78");
					}else if(compare(2,1,2,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 79");
					}else if(compare(0,2,2,4,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 80");
					}else if(compare(2,0,4,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 81");
					}else if(compare(2,4,0,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 82");
					}else if(compare(4,2,2,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 83");
					}else if(compare(2,0,0,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 84");
					}else if(compare(0,2,2,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 85");
					}else if(compare(4,2,0,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 86");
					}else if(compare(0,4,0,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 87");
					}else if(compare(2,0,4,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 88");
					}else if(compare(0,0,2,4,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 89");
					}else if(compare(2,2,0,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 90");
					}else if(compare(0,2,2,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 91");
					}else if(compare(2,2,2,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 92");
					}else if(compare(2,0,2,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 93");
					}else if(compare(4,4,4,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 94");
					}else if(compare(4,4,0,4,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 95");
					}else if(compare(4,0,4,4,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 96");
					}else if(compare(0,4,4,4,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 97");
					}else if(compare(1,0,2,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 98");
					}else if(compare(2,1,0,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 99");
					}else if(compare(0,0,1,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 100");
					}else if(compare(0,2,0,1,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 101");
					}else if(compare(0,1,0,2,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 102");
					}else if(compare(0,0,2,1,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 103");
					}else if(compare(1,2,0,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 104");
					}else if(compare(2,0,1,0,a1-height,a2-height,a3-height,a4-height)){
						out+=("\"modelid\": 105");
					}else if(compare(0,4,0,4,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 106");
						else
							out+=("\"modelid\": 107");
					}else if(compare(0,0,4,4,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 108");
						else
							out+=("\"modelid\": 109");
					}else if(compare(4,4,0,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 110");
						else
							out+=("\"modelid\": 111");
					}else if(compare(4,0,4,0,a1-height,a2-height,a3-height,a4-height)){
						if(number<0.5)
							out+=("\"modelid\": 112");
						else
							out+=("\"modelid\": 113");
					}
					if(out.charAt(out.length()-1)=='{')
						System.out.println(a1+" "+a2+" "+a3+" "+a4+" "+b1+" "+b2+" "+b3+" "+b4);
					out+=(",\"height\": "+height/2+"},");
					a1=a3;
					a2=a4;
					b1=b3;
					b2=b4;
				}
				out+="\n";
			}
			PrintWriter output = new PrintWriter("c:/Mapout.txt");
			output.println(out);
			output.close();
		}catch(Exception ex){
			
		}
		
	}
	private static int minheight(int a1, int a2, int a3, int a4) {
		int min=a1;
		min=(min<a2)?min:a2;
		min=(min<a3)?min:a3;
		min=(min<a4)?min:a4;
		
		return (int) (Math.floor(min/2.0)*2);
	}
	public static boolean compare(int p,int q,int r,int s,int a1,int a2,int a3,int a4){
		if(p==a1 && q==a2 && r==a3 && s==a4)
			return true;
		else
			return false;
	}
	public static boolean compare(String p,String q,String r,String s,String a1,String a2,String a3,String a4){
		if(!a1.equals("D"))
			a1="G";
		if(!a2.equals("D"))
			a2="G";
		if(!a3.equals("D"))
			a3="G";
		if(!a4.equals("D"))
			a4="G";
		if(p.equals(a1) && q.equals(a2) && r.equals(a3) && s.equals(a4))
			return true;
		else
			return false;
	}
	
}
