import java.io.*;
import java.net.*;
import java.util.*;

// Main driver for the programs; starts the nodes
public class StartNode
{
    public static void main( String[] args )
    {
        // Variables necessary for gathering user input
        Scanner userInput;
        InetAddress userAddress;
        InetAddress userIP;
        String[] userAddressSplit;
        String userName;
        int portNumber;
        Node newNode;

        try
        {
            // Get the user's Hostname/IP address
            userAddress = InetAddress.getLocalHost();

            // Split userAddress for IP Address
            userAddressSplit = userAddress.toString().split( "/" );

            // Converting String object of IP address to InetAddress object
            userIP = InetAddress.getByName( userAddressSplit[1] );

            System.out.print( "Please enter your username: " );

            // Scan in user's userName and store result
            userInput = new Scanner( System.in );
            userName = userInput.nextLine();

            // Ask user for their port number from terminal/cmd
            Socket activePort = findActivePort( userIP );
            if ( activePort == null )
            {
                portNumber = 1024;
                newNode = new Node( userName, userIP, portNumber );
                newNode.addNodeData( newNode.getCurrentNode() );
                printConfirmation( newNode );
                newNode.startReceiver();
                newNode.startSender();
            }
            else
            {
                portNumber = getInactivePort( userIP );
                newNode = new Node( userName, userIP, portNumber );
                printConfirmation( newNode );
                newNode.startReceiver();
                getUpdatedInfo( activePort,newNode );
                newNode.startSender();
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        System.exit( 1 );
    }

    // Allows us to check if node not active
    public static int getInactivePort( InetAddress userIP )
    {
        int portNum = 1024;
        while ( true )
        {
            try
            {
                Socket testSocket = new Socket( userIP.getHostAddress(),portNum );
                testSocket.close();
                portNum++;
            }
            catch ( Exception ex )
            {
                return portNum;
            }
        }
    }

    // Port between 1024 and 1050
    public static Socket findActivePort( InetAddress userIP )
    {
        for ( int i = 1024; i < 1050; i++ )
        {
            try
            {
                Socket testSocket = new Socket( userIP.getHostAddress(),i );
                return testSocket;
            }
            catch ( Exception ex )
            {
                i++;
            }
        }
        return null;
    }

    // Updates nodeInfo for this class
    public static void getUpdatedInfo( Socket inSocket, Node newNode )
    {
        try
        {
            ObjectOutputStream toNode = new ObjectOutputStream( inSocket.getOutputStream() );

            // Sends back updated NodeInfo to the new Node trying to connect.
            Message joinMessage = new Message( newNode.getNodeInfo(), newNode.getCurrentNode(), 100, "" );
            toNode.writeObject( joinMessage );
        }

        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }

    // Print function
    public static void printConfirmation( Node inNode )
    {
        System.out.println( inNode.getCurrentNode()[0] + " has entered the chat." );
        System.out.println( "\nYou can start typing your messages...\n" );
    }

}
