import tcpclient.TCPClient;
import java.net.*;
import java.io.*;
import myrunnable.MyRunnable;

public class ConcHTTPAsk {

    public static void main(String[] args) throws IOException {


        ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));

        while(true)
        {
            Socket client = server.accept();
            MyRunnable runnable = new MyRunnable(client);
            Thread rc = new Thread(runnable);
            rc.start();

        }

    }
}
