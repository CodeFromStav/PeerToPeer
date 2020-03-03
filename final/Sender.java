import java.net.*;
import java.io.*;
import java.util.*;

public class Sender extends MessageTypes implements Runnable
{
    private Thread currentThread;
    private NodeInfo nodeInfo;
    private String[] currentNode;
    private Node inNode;
    public Sender(NodeInfo nodeInfo, String[] currentNode, Node inNode)
    {
        this.currentNode = currentNode;
        this.nodeInfo = nodeInfo;
        this.inNode = inNode;
    }

    public void run()
    {
        Scanner userInput = new Scanner( System.in );// = new Scanner( System.in );
        String messageBody = "";// = userInput.nextLine();
        while (!messageBody.contains("leave"))
        {
            try
            {
                messageBody = userInput.nextLine();
                if (!messageBody.contains("leave"))
                {

                }

            }
            catch (Exception ex)
            {

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
