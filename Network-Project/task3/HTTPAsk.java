import tcpclient.TCPClient;
import java.net.*;
import java.io.*;

public class HTTPAsk {

    public static void main(String[] args) throws IOException {


        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));

        while(true)
        {
            Socket client = server.accept();  //connect with client

            String hostname=null;
            int port = 0;
            String MSG200="HTTP/1.1 200 OK\r\n\r\n";
            String MSG400="HTTP/1.1 400 Bad Request\n\n";
            String MSG404="HTTP/1.1 404 Not Found\n\n";
            String argument= "";
            byte [] informationToServer  = new byte[1000];

            //in this part we have all information we need to to get information from client
            int length= client.getInputStream().read(informationToServer);
            String txtToFetch = new String(informationToServer,0,length);
            String [] fetch = txtToFetch.split("[?&= ]", 15);


            if(txtToFetch.contains("HTTP/1.1") && fetch[0].equals("GET") && fetch[1].equals("/ask"))
            {
                for(int i = 0; i<fetch.length; i++)
                {
                    if(fetch[i].equals("hostname"))
                        hostname=fetch[i+1];

                    if(fetch[i].equals("string"))
                        argument=fetch[i+1];

                    if(fetch[i].equals("port"))
                        port= Integer.parseInt(fetch[i+1]);

                }

                try {
                    byte [] argToServer = argument.getBytes("UTF-8");
                    TCPClient clientObject = new TCPClient(false, null, null);
                    byte[] dataToClient = clientObject.askServer(hostname, port, argToServer);
                    client.getOutputStream().write(MSG200.getBytes("UTF-8"));
                    client.getOutputStream().write(dataToClient);


                } catch (IOException e) {
                    client.getOutputStream().write(MSG404.getBytes());
                }
            }else
            {
                client.getOutputStream().write(MSG400.getBytes());
            }


            client.close();




        }

    }
}
