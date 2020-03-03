import java.net.*;
import java.io.*;

/* Class to sepearate instances of server and client connections */
public class Receiver implements Runnable
{
    // running thread associated with client
    Thread currentThread;
    NodeInfo nodeInfo;
    String[] currentNode;
    Node inNode;
    // socket associated with client's connection
    public Receiver(NodeInfo nodeInfo, String[] currentNode, Node inNode)
    {
        this.nodeInfo = nodeInfo;
        this.currentNode = currentNode;
        this.inNode = inNode;
    }
    public void run()
    {
        //while (checkActive(Integer.parseInt(currentNode[2])));
        //{
            try
            {
                ServerSocket server = new ServerSocket(Integer.parseInt(currentNode[2]));
                boolean checkFlag = true;
                while (true)
                {
                    Socket serverClient = server.accept();
                    //System.out.println("hey sexy....");
                    ReceiverHelper inMessage = new ReceiverHelper(serverClient,nodeInfo,currentNode, inNode);
                    inMessage.start();
                    //checkFlag = checkActive( Integer.parseInt( currentNode[2] ));
                }
            }
            catch ( Exception ex )
            {
                //ex.printStackTrace();
            }
        //}
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
    public boolean checkActive(int inPort)
    {
        if (inNode.getNodeInfo().inChatMesh( inPort  ))
        {
            return true;
        }
        return false;
    }
}
