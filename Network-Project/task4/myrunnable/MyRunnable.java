package myrunnable;

import tcpclient.TCPClient;

import java.io.IOException;
import java.net.Socket;

public class MyRunnable implements Runnable {

    Socket thread;

    public MyRunnable(Socket socket)

    {
        this.thread = socket;
    }

    public void run() {


        try {
            String hostname = null;
            int port = 0;
            String MSG200 = "HTTP/1.1 200 OK\r\n\r\n";
            String MSG400 = "HTTP/1.1 400 Bad Request\n\n";
            String MSG404 = "HTTP/1.1 404 Not Found\n\n";
            String argument = "";
            byte[] informationToServer = new byte[1000];

            //in this part we have all information we need to to get information from client
            int length = thread.getInputStream().read(informationToServer);
            String txtToFetch = new String(informationToServer, 0, length);
            String[] fetch = txtToFetch.split("[?&= ]", 15);


            if (txtToFetch.contains("HTTP/1.1") && fetch[0].equals("GET") && fetch[1].equals("/ask")) {
                for (int i = 0; i < fetch.length; i++) {
                    if (fetch[i].equals("hostname"))
                        hostname = fetch[i + 1];

                    if (fetch[i].equals("string"))
                        argument = fetch[i + 1];

                    if (fetch[i].equals("port"))
                        port = Integer.parseInt(fetch[i + 1]);

                }

                try {
                    byte[] argToServer = argument.getBytes("UTF-8");
                    TCPClient clientObject = new TCPClient(false, null, null);
                    byte[] dataToClient = clientObject.askServer(hostname, port, argToServer);
                    thread.getOutputStream().write(MSG200.getBytes("UTF-8"));
                    thread.getOutputStream().write(dataToClient);


                } catch (IOException e) {
                    thread.getOutputStream().write(MSG404.getBytes());
                }
            } else {
                thread.getOutputStream().write(MSG400.getBytes());
            }

            thread.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
