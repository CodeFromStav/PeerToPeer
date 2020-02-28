import java.net.*;
import java.io.*;

/* Test class to verify server-client connection functionality */
public class Node
{
    public static IPAddress nodeIP;
    public static NodeInfo chatMesh;
    public static Socket nodeSocket;

    public static void main(String[] args) throws Exception
    {
        //NodeInfo
        IPAddress ip = new IPAddress( "127.0.0.1" );
        nodeIP = initializeNode(ip);
        startReceiver(nodeIP);
        startSender();
    }

    /*Returns IP that the node is able to use for its connection information
    and also opens up a socket connection with an available node on the network
    to retrieve its nodeInfo for future communications in the mesh*/
    private static IPAddress initializeNode(IPAddress ip) throws Exception
    {
        String ipString = ip.toString();
        /*if server checks to the end of the subnet and no ip's are active,
        it starts serving on localhost to initialize the chat*/
        if ( checkSubnetMax( ipString ) )
        {
            System.out.println( "server is down, initializing..." );
            IPAddress local = new IPAddress( "127.0.0.1" );
            return local;
        }
        try
        {
            nodeSocket = new Socket( ipString, 8080 );
            ip.incrementIP();
            IPAddress availableIP = findAvailableIP(ip);
            return availableIP;
        }
        catch ( Exception ex ) {
            System.out.println( "IP " + ipString + " is not on network." );
            ip.incrementIP();
            return initializeNode(ip);
        }
    }

    private static void startReceiver(IPAddress ip) throws Exception
    {
        /*if server is not full...*/
        if ( ip != null )
        {
            String ipString = ip.toString();
            InetAddress address = InetAddress.getByName(ipString);
            try
            {
                /*If the node is serving on localhost, it is the first node in the
                chat app*/
                if (ipString == "127.0.0.1")
                {
                    System.out.println("Chatnode 1 started...");
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }

    private static IPAddress findAvailableIP( IPAddress ip )
    {
        String ipString = ip.toString();
        if ( checkSubnetMax( ipString ) )
        {
            System.out.println( "Sorry bro, the server is full." );
            return null;
        }
        try
        {
            /*if a socket is established, that ip is being used, so the next ip
            is checked to see if it is available.*/
            Socket testSocket = new Socket( ipString,8080 );
            testSocket.close();
            ip.incrementIP();
            return findAvailableIP( ip );
        }
        /*In this function, failure is good. When the function fails it means the
        ip is available for the node to use.*/
        catch ( Exception ex )
        {
            return ip;
        }
    }
    private static boolean checkSubnetMax( String ipString )
    {
        return ipString == "127.255.255.250";
    }

    private static void StartSender()
    {
        
    }
}
