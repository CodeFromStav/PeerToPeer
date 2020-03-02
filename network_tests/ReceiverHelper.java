import java.net.*;
import java.io.*;
public class ReceiverHelper extends MessageTypes implements Runnable
{
    Thread currentThread;
    Socket currentSocket;
    Message currentMessage;
    Node currentNode;
    NodeInfo currentMesh;
    Node referenceNode;
    ReceiverHelper( Socket inSocket, Node currentNode )
    {
        currentSocket = inSocket;
        this.currentNode = currentNode;
    }
    public void run()
    {
        try
        {
            System.out.println("i am receiving!!");

            ObjectInputStream fromNode = new ObjectInputStream( currentSocket.getInputStream() );
            currentMessage = (Message) fromNode.readObject();
            System.out.println("i read a messages");
            switch (currentMessage.getCode())
            {
                case LEAVE_CODE:
                    break;
                case NOTE_CODE:
                    break;
                case JOIN_CODE:
                    referenceNode = currentMessage.getNode();
                    currentNode.updateMesh(referenceNode);
                    ObjectOutputStream toNode = new ObjectOutputStream( currentSocket.getOutputStream() );
                    Message joinMessage = new Message(currentNode,"",101);
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
