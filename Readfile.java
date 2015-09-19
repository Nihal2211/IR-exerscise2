import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Readfile{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static TreeMap <String, HashMap <Integer, ArrayList>> invertedIndex = new TreeMap<String,HashMap <Integer, ArrayList>>();

	public Readfile(){
	}
	
	public static void CreatingInvertedIndex(int docno, String doc){

		//System.out.println(doc);
		doc = doc.toLowerCase();
		String[] line = doc.split("\n");
		//System.out.println("yeah" + line.length);

		int positionNumber=0;
		HashMap <String, ArrayList> docMap = new HashMap <String, ArrayList>();
		//DocMap is the local index
		for (int a =0; a< line.length; a++) {            
			// process the line.
			if (line[a] == null) {break;}
			line[a]=line[a].trim();
			String[] wordlist = line[a].split(" ");	
			//System.out.println(wordlist[0] + "This is first word");
			for ( int i =0 ; i <wordlist.length; i ++) {
				positionNumber +=1;
				ArrayList <Integer> positionArray = new ArrayList <Integer>();
				boolean isObjectKeyInHashMap = docMap.containsKey(wordlist[i]);

				if (wordlist[i].endsWith(".") ){
					wordlist[i]= wordlist[i].substring(0, wordlist[i].length() - 1);         
				}
				if(wordlist[i].matches("[a-zA-Z]*$") && !wordlist[i].matches("^\\s*$")){ 

					if (isObjectKeyInHashMap == false ) {	
						positionArray.add(positionNumber);
						docMap.put(wordlist[i],positionArray);
					}
					else {
						positionArray = docMap.get(wordlist[i]);
						positionArray.add(positionNumber);
					}
				}  
			}
		} 

		//merge lineMap (localindex) and global index

		for (String index : docMap.keySet())
		{
			//System.out.println("Index keys" + index);
			ArrayList <Integer> temp = new ArrayList <Integer>();
			HashMap <Integer, ArrayList> lineMapinInverted =  new HashMap <Integer, ArrayList>();
			temp = 	docMap.get(index);
			if (invertedIndex.containsKey(index)){ 
				lineMapinInverted = invertedIndex.get(index);
				lineMapinInverted.put(docno, temp);

			}
			else {
				temp=docMap.get(index);
				lineMapinInverted.put(docno, temp);
				invertedIndex.put(index, lineMapinInverted);
			}

		}
		
	}

	
	

	public static void main (String[] args){
		Readfile main = new Readfile();
		
		//index.lines ="This\n This is.";
		ArrayList<String> docArray = new ArrayList<String>();
		File directory = new File("C:/Users/syedkhadri/workspace2/Trail/src/Index4file");
		for (final File file : directory.listFiles()) {
			System.out.println(file);
			docArray.add("C:/Users/syedkhadri/workspace2/Trail/src/Index4file/" + file.getName());
		}


		try
		{
			for (int docno =0; docno < docArray.size(); docno++){
				System.out.println(docArray.get(docno));
				FileInputStream fileIn = new FileInputStream(docArray.get(docno));
				ObjectInputStream in = new ObjectInputStream(fileIn); 		
				ObjectToWrite box = (ObjectToWrite)in.readObject();	
	            //index.setline(box.getline());
	            System.out.println(box.getline());
				CreatingInvertedIndex(docno+1, box.getline());
				
				in.close();
				fileIn.close();
			}
			FileOutputStream fileOut =
					new FileOutputStream("C:/Users/syedkhadri/desktop/result4.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			System.out.println(main.invertedIndex);
			out.writeObject(main.invertedIndex);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in result1.ser");

			
		}catch(IOException i)
		{
			i.printStackTrace();
		}
		catch(ClassNotFoundException c)
		{
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}
}
