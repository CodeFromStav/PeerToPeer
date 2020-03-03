import java.net.*;
import java.io.*;
import java.util.*;

public class Sender extends MessageTypes// implements Runnable
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
        run();
    }

    /*public void run()
    {
        Scanner userInput = new Scanner( System.in );// = new Scanner( System.in );
        String messageBody = "";// = userInput.nextLine();
        //System.out.println("Sender Started");
        while (!messageBody.contains("leave"))
        {
            try
            {
                messageBody = userInput.nextLine();
                if (!messageBody.contains("leave"))
                {
                    Message msg = new Message(inNode.getNodeInfo(), inNode.getCurrentNode(), 110, messageBody);
                    //System.out.println(inNode.nodeInfoToString());
                    sendMessage(msg, inNode.getNodeInfo());
                }
                else
                {
                    Message msg = new Message(inNode.getNodeInfo(), inNode.getCurrentNode(), 105, "");

                    sendMessage(msg, inNode.getNodeInfo());
                    inNode.removeNode( Integer.parseInt(currentNode[2] ));
                    //System.out.println( "Goodbye " + currentNode[0] + "." );
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }*/
    public void run()
    {
        Scanner userInput = new Scanner( System.in );// = new Scanner( System.in );
        String messageBody = "";// = userInput.nextLine();
        //System.out.println("Sender Started");
        while (!messageBody.contains("leave"))
        {
            try
            {
                messageBody = userInput.nextLine();
                if (!messageBody.contains("leave"))
                {
                    Message msg = new Message(inNode.getNodeInfo(), inNode.getCurrentNode(), 110, messageBody);
                    //System.out.println(inNode.nodeInfoToString());
                    sendMessage(msg, inNode.getNodeInfo());
                }
                else
                {
                    Message msg = new Message(inNode.getNodeInfo(), inNode.getCurrentNode(), 105, "");

                    sendMessage(msg, inNode.getNodeInfo());
                    inNode.removeNode( Integer.parseInt(currentNode[2] ));
                    //System.out.println( "Goodbye " + currentNode[0] + "." );
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /* Creates a new thread to start a server-client connection */
    /*public void start()
    {
        if (currentThread == null)
        {
            currentThread = new Thread(this);
            currentThread.start();
        }
    }*/
    public void sendMessage(Message msg, NodeInfo nodeInfo)
    {
        try
        {
            for (int i = 0; i < nodeInfo.getSize(); i++)
            {
                if (!nodeInfo.get(i)[2].equals(inNode.getCurrentNode()[2]))
                {
                    Socket toNode = new Socket(nodeInfo.get(i)[1], Integer.parseInt(nodeInfo.get(i)[2]));
                    ObjectOutputStream out = new ObjectOutputStream( toNode.getOutputStream() );
                    //System.out.println("Sending message...");
                    out.writeObject(msg);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
