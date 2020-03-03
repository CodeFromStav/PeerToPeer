import java.net.*;
import java.io.*;
public class ReceiverHelper extends MessageTypes implements Runnable
{
    Thread currentThread;
    Socket currentSocket;
    NodeInfo nodeInfo;
    String[] currentNode;
    Node inNode;
    ReceiverHelper(  Socket inSocket, NodeInfo nodeInfo, String[] currentNode, Node inNode)
    {
        currentSocket = inSocket;
        this.nodeInfo = nodeInfo;
        this.currentNode = currentNode;
        this.inNode = inNode;
    }
    public void run( )
    {
        try
        {
            ObjectInputStream fromNode = new ObjectInputStream(  currentSocket.getInputStream( ) );
            Message currentMessage = ( Message) fromNode.readObject( );
            //System.out.println( "connectingNode info: " + currentMessage.getCurrentNode( )[0]);
            switch ( currentMessage.getCode( ))
            {
                case JOINED_CODE:
                    //System.out.println( "Node: " + currentMessage.getCurrentNode( )[0] + " has joined");

                    inNode.updateMesh( currentMessage.getNodeInfo( ));

                    //System.out.println( "updated nodeList is..." + inNode.nodeInfoToString( ));
                    break;
                case LEAVE_CODE:
                    System.out.println( currentMessage.getCurrentNode( )[0] + " has left the chat...");
                    inNode.removeNode(  Integer.parseInt( currentMessage.getCurrentNode( )[2]));
                    //System.out.println( "updated nodeList is..." + inNode.nodeInfoToString( ) + "After leave");

                    break;
                case NOTE_CODE:
                    System.out.println( currentMessage.getMsg( ));
                    break;
                case JOIN_CODE:
                    // Updates the already connected node with new node's information
                    inNode.addNodeData( currentMessage.getCurrentNode( ));


                    // Send back the updated ArrayList to the newly connected node
                    //ObjectOutputStream toNode = new ObjectOutputStream(  currentSocket.getOutputStream( ) );

                    // sends back updated NodeInfo to the new Node trying to connect.
                    //System.out.println( "Node: " + currentMessage.getCurrentNode( )[0] + " has joined.");
                    //Message joinMessage = new Message(  nodeInfo, currentNode, JOINED_CODE, "" );
                    //toNode.writeObject(  joinMessage );
                    //System.out.println( "Join message sent to " + currentMessage.getCurrentNode( )[0]);
                    sendConfirmation(  inNode.getNodeInfo( ),currentNode );

                    break;
            }
            /*
            * Implementation of the switch statement of message codes. object input stream should
            * be able to grab the message class's getCode( ) method to find out which type of message it is
            *
            *
            *
            */
            //fromNode.close( );
            currentSocket.close( );
        }
        /*catch (  IOException ex )
        {
            //System.out.println(  ex );
            ex.printStackTrace( );
        }
        catch (  ClassNotFoundException ex )
        {
            ex.printStackTrace( );
        }*/
        /* Throws EOF Exception to signal end of input stream */
        catch (  Exception ex )
        {
            //ex.printStackTrace( );
        }
    }

    public void sendConfirmation( NodeInfo inNodeInfo, String[] currentNode)
    {
        try
        {
            for ( int i = 0; i < inNodeInfo.getSize( ); i++) {
                if ( !inNodeInfo.get( i)[2].equals( currentNode[2]))
                {
                    //System.out.println( "New Node Received, sending to : " + inNodeInfo.get( i)[0]);

                    //System.out.println( "Trying to send confirmation to port: " + inNodeInfo.get( i)[1] + " " + inNodeInfo.get( i)[2]);
                    Socket sendSocket = new Socket( inNodeInfo.get( i)[1], Integer.parseInt( inNodeInfo.get( i)[2]));
                    Message joinedMessage = new Message(  inNodeInfo, currentNode, JOINED_CODE, "" );
                    ObjectOutputStream toMesh = new ObjectOutputStream(  sendSocket.getOutputStream( ) );
                    //System.out.println( "sending confirmation to: " + inNodeInfo.get( i)[0]);
                    toMesh.writeObject(  joinedMessage );
                }
            }
        }
        catch (  Exception e )
        {
            e.printStackTrace( );
        }
    }
    public void start( )
    {
        if ( currentThread == null)
        {
            currentThread = new Thread(  this );
            currentThread.start( );
        }
    }
}
