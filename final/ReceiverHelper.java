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
            System.out.println("i am receiving!!");

            ObjectInputStream fromNode = new ObjectInputStream( currentSocket.getInputStream() );
            Message currentMessage = (Message) fromNode.readObject();
            System.out.println("i read a messages");
            switch (currentMessage.getCode())
            {
                case LEAVE_CODE:
                    break;
                case NOTE_CODE:
                    break;
                case JOIN_CODE:
                    // Updates the already connected node with new node's information
                    nodeInfo.update(currentMessage.getCurrentNode());
                    ObjectOutputStream toNode = new ObjectOutputStream( currentSocket.getOutputStream() );

                    // sends back updated NodeInfo to the new Node trying to connect.
                    Message joinMessage = new Message( nodeInfo, currentNode, JOINED_CODE, "" );
                    toNode.writeObject( joinMessage );
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
