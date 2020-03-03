import java.net.*;
import java.io.*;

/* Class to sepearate instances of server and client connections */
public class Receiver implements Runnable
{
    // running thread associated with client
    Thread currentThread;
    NodeInfo nodeInfo;
    String[] currentNode;
    // socket associated with client's connection
    public Receiver(NodeInfo nodeInfo, String[] currentNode)
    {
        this.nodeInfo = nodeInfo;
        this.currentNode = currentNode;
    }
    public void run()
    {
        while (true)
        {
            try
            {
                ServerSocket server = new ServerSocket(Integer.parseInt(currentNode[2]));
                while (true)
                {
                    Socket serverClient = server.accept();
                    //System.out.println("hey sexy....");
                    ReceiverHelper inMessage = new ReceiverHelper(serverClient,nodeInfo,currentNode);
                    inMessage.start();
                }
            }
            catch ( Exception ex )
            {
                //ex.printStackTrace();
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
