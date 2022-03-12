package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public TCPClient() {
    }

    
    
    /*
     @author Hasan Alzubeidi
     step-1)writes bytes to server
     step-2)read byte from the server and store them in dynamsik array 
     */

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {



            
    	    Socket task1 = new Socket(hostname, port);
            ByteArrayOutputStream bytesFromTheServer = new ByteArrayOutputStream();
            byte[] oneByte = new byte[1]; 
            
           
            task1.getOutputStream().write(toServerBytes, 0, toServerBytes.length);  
            
            
            int x = 0;
            while ((x = task1.getInputStream().read()) != -1) {
            	oneByte[0]= (byte) x;
            	bytesFromTheServer.write(oneByte,0,1);
            }

           
            task1.close();

    return bytesFromTheServer.toByteArray() ;
    }
    
    
    
}
