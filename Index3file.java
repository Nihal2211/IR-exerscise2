
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.DataOutputStream;

public class Index3file {
	
	static TreeMap <String, HashMap <Integer, ArrayList>> invertedIndex = new TreeMap<String,HashMap <Integer, ArrayList>>();
	
  private static TreeMap <String, HashMap <Integer, ArrayList>> 
  indexing(int docno, String doc) {

			
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
		
			return invertedIndex; 
  }	
	
   public static void main(String[] args) throws IOException {
      
      
      ArrayList<String> docArray = new ArrayList();
      ArrayList<String> words = new ArrayList<String>();
      File directory = new File("C:/Users/syedkhadri/workspace2/Trail/src/Wordfrequency");
		for (final File file : directory.listFiles()) {
			System.out.println(file);
			docArray.add("C:/Users/syedkhadri/workspace2/Trail/src/Wordfrequency/" + file.getName());
		}
		
		
		InputStream is = null;
	      DataInputStream dis = null;
	      FileOutputStream fos = null;
	      DataOutputStream dos = null;
			try{
				
				for (int docno = 0; docno <docArray.size(); docno++ ){
					
         // create file input stream
         is = new FileInputStream(docArray.get(docno));
         
         // create new data input stream
         dis = new DataInputStream(is);
         
         // available stream to be read
         int length = dis.available();
         
         // create buffer
         byte[] buf = new byte[length];
         
         // read the full data into the buffer
         dis.readFully(buf);
         
         // for each byte in the buffer
        /* for (byte b:buf)
         {
            // convert byte to char
            
           
            // prints character
            System.out.println(b);
         }*/
         //String c = buf.toString();
         String c = new String(buf);
         indexing(docno +1,c);
         
         
         //words.add(c);
	   }
				System.out.println(invertedIndex);
         fos = new FileOutputStream("C:/Users/syedkhadri/desktop/word1.txt");
         
         // create data output stream
         dos = new DataOutputStream(fos);
           
         // for each  string in string buffer
         for(Entry<String, HashMap<Integer, ArrayList>> entry: invertedIndex.entrySet()) {
        	    dos.writeUTF(entry.getKey());
        	    for(Entry<Integer, ArrayList> entry2: entry.getValue().entrySet()) {
        	    	dos.writeInt(entry2.getKey());
        	    	
        	    	 //for(ArrayList<Integer> entry3: entry2.getValue()) {
        	    	//	 dos.writeInt(entry3);}
        	    }
        	    
        	}
         // force data to the underlying file output stream
         dos.flush();
         
       
      }catch(Exception e){
         // if any error occurs
         e.printStackTrace();
      }finally{
         
         // releases all system resources from the streams
         if(is!=null)
            is.close();
         if(dis!=null)
            dis.close();
      }
			}
		
   }	
