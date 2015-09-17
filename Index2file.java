
//version which asks to look at a folder 
//go through documents and check the position numbers

import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class Index2file {


	public static TreeMap<String,HashMap <Integer, ArrayList>> printer () {

		ArrayList<String> Docarray = new ArrayList();
		File directory = new File("C:/Users/syedkhadri/workspace2/Trail/src/Wordfrequency");
		for (final File file : directory.listFiles()) {
			System.out.println(file);
			Docarray.add("C:/Users/syedkhadri/workspace2/Trail/src/Wordfrequency/" + file.getName());
		}
		HashMap <String, HashMap <Integer, ArrayList>> invertedIndex = new HashMap<String,HashMap <Integer, ArrayList>>();

		for (int docno = 0; docno <Docarray.size(); docno++ ){

			try (BufferedReader br = new BufferedReader(new FileReader(Docarray.get(docno))))
			{

				String line;
				int positionNumber=0;
				HashMap <String, ArrayList> docMap = new HashMap <String, ArrayList>();
				//DocMap is the local index
				while ((line = br.readLine()) != null) {

					line = line.toLowerCase(); // process the line.
					if (line == null) {break;}
					line = line.trim();
					String[] wordlist = line.split(" ");
					

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
					ArrayList <Integer> temp = new ArrayList <Integer>();
					HashMap <Integer, ArrayList> lineMapinInverted =  new HashMap <Integer, ArrayList>();
					temp = 	docMap.get(index);
					if (invertedIndex.containsKey(index)){ 
						lineMapinInverted = invertedIndex.get(index);
						lineMapinInverted.put(docno +1, temp);

					}
					else {
						temp=docMap.get(index);
						lineMapinInverted.put(docno +1, temp);
						invertedIndex.put(index, lineMapinInverted);
					}

				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}   
		}
		TreeMap <String,HashMap <Integer, ArrayList>> map = new TreeMap <String, HashMap <Integer, ArrayList>>(invertedIndex);
		return map;
	}

	public static void main(String[] args) {
		System.out.println(printer());
	}

}

