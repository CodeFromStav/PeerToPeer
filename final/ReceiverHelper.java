import java.net.*;
import java.io.*;
public class ReceiverHelper extends MessageTypes implements Runnable
{
    Thread currentThread;
    Socket currentSocket;
    NodeInfo nodeInfo;
    String[] currentNode;
    ReceiverHelper( Socket inSocket, NodeInfo nodeInfo, String[] currentNode)
    {
        currentSocket = inSocket;
        this.nodeInfo = nodeInfo;
        this.currentNode = currentNode;
    }
    public void run()
    {
        try
        {
            ObjectInputStream fromNode = new ObjectInputStream( currentSocket.getInputStream() );
            Message currentMessage = (Message) fromNode.readObject();
            System.out.println("connectingNode info: " + currentMessage.getCurrentNode()[0]);
            switch (currentMessage.getCode())
            {
                case JOINED_CODE:
                    System.out.println("confirmation received by: " + currentMessage.getCurrentNode()[0]);
                    System.out.println("updated nodeList is..." + currentMessage.getNodeInfo().nodeInfoToString());
                    break;
                case LEAVE_CODE:
                    break;
                case NOTE_CODE:
                    System.out.println(currentMessage.getMsg());
                    break;
                case JOIN_CODE:
                    // Updates the already connected node with new node's information
                    nodeInfo.update(currentMessage.getCurrentNode());

                    // Send back the updated ArrayList to the newly connected node
                    ObjectOutputStream toNode = new ObjectOutputStream( currentSocket.getOutputStream() );

                    // sends back updated NodeInfo to the new Node trying to connect.
                    System.out.println("Node: " + currentMessage.getCurrentNode()[0] + " Wants to join...");
                    Message joinMessage = new Message( nodeInfo, currentNode, JOINED_CODE, "" );
                    toNode.writeObject( joinMessage );
                    System.out.println("Join message sent to " + currentMessage.getCurrentNode()[0]);
                    sendConfirmation( nodeInfo,currentNode );

                    break;
            }
            /*
            * Implementation of the switch statement of message codes. object input stream should
            * be able to grab the message class's getCode() method to find out which type of message it is
            *
            *
            *
            */
            //fromNode.close();
        }
        catch ( IOException ex )
        {
            //System.out.println( ex );
        }
        catch ( ClassNotFoundException ex )
        {

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }

    public void sendConfirmation(NodeInfo inNodeInfo, String[] currentNode)
    {
        try
        {
            for (int i = 0; i < inNodeInfo.getSize(); i++) {
                if (!inNodeInfo.get(i)[2].equals(currentNode[2]))
                {
                    System.out.println("New Node Received, sending : " + inNodeInfo.nodeInfoToString());

                    System.out.println("Trying to send confirmation to port: " + inNodeInfo.get(i)[1] + " " + inNodeInfo.get(i)[2]);
                    Socket sendSocket = new Socket(inNodeInfo.get(i)[1], Integer.parseInt(inNodeInfo.get(i)[2]));
                    Message joinedMessage = new Message( inNodeInfo, currentNode, JOINED_CODE, "" );
                    ObjectOutputStream toMesh = new ObjectOutputStream( sendSocket.getOutputStream() );
                    toMesh.writeObject( joinedMessage );
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /* Creates a new thread to start a server-client connection */
    public void start()
    {
        if (currentThread == null)
        {
            currentThread = new Thread( this );
            currentThread.start();
        }
    }
}
