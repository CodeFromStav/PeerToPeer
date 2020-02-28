import java.net.*;
import java.io.*;

/* Class to sepearate instances of server and client connections */
public class Receiver implements Runnable
{
    // running thread associated with client
    Thread currentThread;
    // socket associated with client's connection
    IPAddress nodeIP;

    Receiver(IPAddress ip)
    {
        nodeIP = ip;
    }
    /* Runnable portion of the class, opens up a connection between the client and
    server */
    public void run()
    {
        while (true)
        {
            try
            {
                InetAddress nodeAddress = InetAddress.getByName(nodeIP.toString());
                ServerSocket server = new ServerSocket(8080,50,nodeAddress);
                while (true)
                {
                    Socket serverClient = server.accept();
                    System.out.println("Someone is trying to message");
                    ReceiverHelper inMessage = new ReceiverHelper(serverClient);
                    inMessage.start();
                }
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
        }
    }

    /* Creates a new thread to start a server-client connection */
    public void start()
    {
        if (currentThread == null)
        {
            currentThread = new Thread(this);
            currentThread.start();
        }
    }
}
