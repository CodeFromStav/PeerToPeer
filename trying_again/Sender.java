import java.net.*;
import java.io.*;

public class Sender extends MessageTypes implements Runnable
{
    private Thread currentThread;
    private NodeInfo nodeInfo;
    private String[] currentNode;
    public Sender(NodeInfo nodeInfo, String[] currentNode)
    {
        this.currentNode = currentNode;
        this.nodeInfo = nodeInfo;
    }

    public void run()
    {

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
