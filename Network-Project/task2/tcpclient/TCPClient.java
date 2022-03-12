package tcpclient;
import java.net.*;
import java.io.*;


public class TCPClient {

    static boolean shutdown =false;
    static Integer timeout=null;
    static Integer limit=null;




    public TCPClient(boolean shutdown, Integer timeout, Integer limit)
    {

        this.shutdown = shutdown;
        this.timeout = timeout;
        this.limit = limit;


    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {




        ByteArrayOutputStream incommingData = new ByteArrayOutputStream();
        byte[] helpArray = new byte[1000];
        int x = 0;

        Socket task2 = new Socket(hostname, port);

        if(timeout!=null)
            task2.setSoTimeout(timeout);

        try {
            task2.getOutputStream().write(toServerBytes, 0, toServerBytes.length);

            if (shutdown)
                task2.shutdownOutput();


            if (limit == null)
                while ((x = task2.getInputStream().read(helpArray)) != -1) {
                    incommingData.write(helpArray, 0, x);
                }
            else {
                Integer a = limit / 1000;
                Integer b = limit % 1000;
                int counter = a + 1;
                if (a != 0)
                    while ((x = task2.getInputStream().read(helpArray)) != -1) {
                        incommingData.write(helpArray);
                        counter--;
                       


                        if (counter == 1) {
                            x = task2.getInputStream().read(helpArray, 0, b);
                            incommingData.write(helpArray, 0, x);
                            break;
                        }
                    }
                else {
                    x = task2.getInputStream().read(helpArray, 0, b);
                    incommingData.write(helpArray, 0, x);

                }


            }

        } catch (SocketTimeoutException e) {
            task2.shutdownOutput();
        }
        finally {
            return incommingData.toByteArray();
        }







    }
}
