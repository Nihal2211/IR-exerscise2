import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Index3file {
   public static void main(String[] args) throws IOException {
      
      InputStream is = null;
      DataInputStream dis = null;
      
      try{
         // create file input stream
         is = new FileInputStream("C:/Users/syedkhadri/workspace2/Trail/src/Wordfrequency");
         
         // create new data input stream
         dis = new DataInputStream(is);
         
         // available stream to be read
         int length = dis.available();
         
         // create buffer
         byte[] buf = new byte[length];
         
         // read the full data into the buffer
         dis.readFully(buf);
         
         // for each byte in the buffer
         for (byte b:buf)
         {
            // convert byte to char
            char c = (char)b; 
            
            // prints character
            System.out.print(c);
         }
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