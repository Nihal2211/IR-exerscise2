
//import

//version which retruns the linenumber and position number

//package javax.annotation.processing;
//import edu.princeton.cs.algs4.StdIn;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.util.TreeMap;
public class Index1file {


	public static TreeMap<String,HashMap <Integer, ArrayList>> printer (String args) {
		// arg2 = Boolean.parseBoolean(args[1]);
		HashMap <String, HashMap <Integer, ArrayList>> invertedIndex = new HashMap<String,HashMap <Integer, ArrayList>>();
		try (BufferedReader br = new BufferedReader(new FileReader(args)))
		{

			String line;
			int linenumber=0;

			while ((line = br.readLine()) != null) {

				line = line.toLowerCase(); // process the line.
				if (line == null) {break;}
				line = line.trim();
				//System.out.print(line);
				String[] wordlist = line.split(" ");
				HashMap <String, ArrayList> lineMap = new HashMap <String, ArrayList>(); //LineMap is the local index

				for ( int i =0 ; i <wordlist.length; i ++) {
					ArrayList <Integer> positionArray = new ArrayList <Integer>();
					boolean isObjectKeyInHashMap = lineMap.containsKey(wordlist[i]);
					//HashMap <Integer, ArrayList> value  = invertedIndex.get(wordlist[i]);
					/*if (value != null) {
						positionArray= value.get(linenumber);
					}*/
					// System.out.println(hash.get(wordlist[i]));
					// System.out.println(delims[i]);
					if (wordlist[i].endsWith(".") ){
						wordlist[i]= wordlist[i].substring(0, wordlist[i].length() - 1);         
					}
					if(wordlist[i].matches("[a-zA-Z]*$") && !wordlist[i].matches("^\\s*$")){ //"[a-zA-Z]*$"
						//System.out.println(linenumber);
						//System.out.println(value);
						if (isObjectKeyInHashMap == false ) {	
						positionArray.add(i);
						lineMap.put(wordlist[i],positionArray);
						}
						else {
							positionArray = lineMap.get(wordlist[i]);
							positionArray.add(i);
							}
						
					}  
				}
				ArrayList <Integer> temp = new ArrayList <Integer>();
				HashMap <Integer, ArrayList> lineMapinInverted =  new HashMap <Integer, ArrayList>();
				//merge lineMap (localindex) and global index
				for (String index : lineMap.keySet())
				 {
					
					temp = 	lineMap.get(index);
					if (invertedIndex.containsKey(index)){ 
					  
					  
					  lineMapinInverted = invertedIndex.get(index);
					  lineMapinInverted.put(linenumber, temp);
					  
					}
					else {
					 temp=lineMap.get(index);
					 lineMapinInverted.put(linenumber, temp);
					 invertedIndex.put(index, lineMapinInverted);
					}
						
				 }
				linenumber = linenumber + 1;
			}        
		}
		catch (IOException e) {
			e.printStackTrace();
		}   

		TreeMap <String,HashMap <Integer, ArrayList>> map = new TreeMap <String, HashMap <Integer, ArrayList>>(invertedIndex);
		return map;
	}

	public static void main(String[] args) {
		System.out.println(printer(args[0]));
	}

}

