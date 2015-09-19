

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.Serializable;

public class Writefile{
	

	public static void main (String[] args){
		ObjectToWrite index = new ObjectToWrite();
		index.setline("\n We should do.\n \n To be or not to be.");
		try
		{

			FileOutputStream fileOut =
					new FileOutputStream("C:/Users/syedkhadri/workspace2/Trail/src/Index4file/word2.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			System.out.println(index.getline());
			out.writeObject(index);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in result.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}
}



 class ObjectToWrite implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lines;
	public ObjectToWrite(){
		
		this.lines= "";
	}
	public String getline(){
		return lines;
	}
	public void setline(String s){
		this.lines = s;
	}
}

