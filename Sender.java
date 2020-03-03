import java.net.*;
import java.io.*;
import java.util.*;

// Handles Note and Leave message types
public class Sender extends MessageTypes
{
    private NodeInfo nodeInfo;
    private String[] currentNode;
    private Node inNode;
    public Sender( NodeInfo nodeInfo, String[] currentNode, Node inNode )
    {
        this.currentNode = currentNode;
        this.nodeInfo = nodeInfo;
        this.inNode = inNode;
        run();
    }

    // Start a thread for Sender
    public void run()
    {
        Scanner userInput = new Scanner(  System.in );
        String messageBody = "";
        
        while ( !messageBody.contains( "leave" ) )
        {
            try
            {
                messageBody = userInput.nextLine();
                if ( !messageBody.contains( "leave" ) )
                {
                    // Sends messages between Node users that don't contain "leave"
                    Message msg = new Message( inNode.getNodeInfo(), inNode.getCurrentNode(), NOTE_CODE, messageBody );
                    sendMessage( msg, inNode.getNodeInfo() );
                }
                else
                {
                    // Sends message to all Nodes to show that a Node is leave chat
                    Message msg = new Message( inNode.getNodeInfo(), inNode.getCurrentNode(), LEAVE_CODE, "" );
                    sendMessage( msg, inNode.getNodeInfo() );
                    inNode.removeNode(  Integer.parseInt( currentNode[2] ) );
                }

            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
        }
    }

    // Sends out message based on type
    public void sendMessage( Message msg, NodeInfo nodeInfo )
    {
        try
        {
            // Iterate through nodeInfo meshData to send message to each Node
            for ( int i = 0; i < nodeInfo.getSize(); i++ )
            {
                if ( !nodeInfo.get( i )[2].equals( inNode.getCurrentNode()[2] ) )
                {
                    Socket toNode = new Socket( nodeInfo.get( i )[1], Integer.parseInt( nodeInfo.get( i )[2] ) );
                    ObjectOutputStream out = new ObjectOutputStream(  toNode.getOutputStream() );
                    out.writeObject( msg );
                }
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
}
