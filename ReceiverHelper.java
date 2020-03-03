import java.net.*;
import java.io.*;

// Helper class for managing message responses based on message types
public class ReceiverHelper extends MessageTypes implements Runnable
{
    Thread currentThread;
    Socket currentSocket;
    NodeInfo nodeInfo;
    String[] currentNode;
    Node inNode;

    ReceiverHelper(  Socket inSocket, NodeInfo nodeInfo, String[] currentNode, Node inNode )
    {
        currentSocket = inSocket;
        this.nodeInfo = nodeInfo;
        this.currentNode = currentNode;
        this.inNode = inNode;
    }

    // Start ReceiverHelper thread
    public void run()
    {
        try
        {
            ObjectInputStream fromNode = new ObjectInputStream(  currentSocket.getInputStream() );
            Message currentMessage = ( Message ) fromNode.readObject();

            switch ( currentMessage.getCode() )
            {
                case JOINED_CODE:

                    inNode.updateMesh( currentMessage.getNodeInfo() );

                    break;
                case LEAVE_CODE:

                    System.out.println( currentMessage.getCurrentNode()[0] + " has left the chat..." );
                    inNode.removeNode(  Integer.parseInt( currentMessage.getCurrentNode()[2] ) );

                    break;
                case NOTE_CODE:

                    System.out.println( currentMessage.getMsg() );
                    break;
                case JOIN_CODE:

                    // Updates the already connected node with new node's information
                    inNode.addNodeData( currentMessage.getCurrentNode() );
                    sendConfirmation(  inNode.getNodeInfo(),currentNode );

                    break;
            }
            currentSocket.close();
        }

        /* Throws EOF Exception to signal end of input stream */
        catch (  Exception ex )
        {
            //ex.printStackTrace();
        }
    }

    // Send confirmation to every node in the NodeInfo meshList
    public void sendConfirmation( NodeInfo inNodeInfo, String[] currentNode )
    {
        try
        {
            for ( int i = 0; i < inNodeInfo.getSize(); i++ ) {
                if ( !inNodeInfo.get( i )[2].equals( currentNode[2] ) )
                {
                    Socket sendSocket = new Socket( inNodeInfo.get( i )[1], Integer.parseInt( inNodeInfo.get( i )[2] ) );
                    Message joinedMessage = new Message(  inNodeInfo, currentNode, JOINED_CODE, "" );
                    ObjectOutputStream toMesh = new ObjectOutputStream(  sendSocket.getOutputStream() );
                    toMesh.writeObject(  joinedMessage );
                }
            }
        }
        catch (  Exception e )
        {
            e.printStackTrace();
        }
    }

    // Create new thread
    public void start()
    {
        if ( currentThread == null )
        {
            currentThread = new Thread( this );
            currentThread.start();
        }
    }
}
