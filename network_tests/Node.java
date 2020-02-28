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
        System.out.println(nodeIP.toString());
        startReceiver(nodeIP);
        startSender();
    }

    /*Returns IP that the node is able to use for its connection information
    and also opens up a socket connection with an available node on the network
    to retrieve its nodeInfo for future communications in the mesh*/
    private static IPAddress initializeNode(IPAddress ip) throws Exception
    {
        while (true)
        {
            String ipString = ip.toString();
            /*if server checks to the end of the subnet and no ip's are active,
            it starts serving on localhost to initialize the chat*/
            if ( checkSubnetMax( ipString ) )
            {
                System.out.println(ipString);
                System.out.println( "server is down, initializing..." );
                IPAddress local = new IPAddress( "127.0.0.1" );
                return local;
            }
            try
            {
                nodeSocket = new Socket( ipString, 8080 );
                /*check to see if the next ip in the subnet is available for the
                node to take*/
                ip.incrementIP();
                IPAddress availableIP = findAvailableIP(ip);
                return availableIP;
            }
            catch ( Exception ex ) {
                System.out.println( "IP-" + ipString + "-is not on network." );
                ip.incrementIP();
            }

        }
    }

    private static void startReceiver(IPAddress ip) throws Exception
    {
        /*if server is not full...*/
        if ( ip != null )
        {
            String ipString = ip.toString();
            try
            {
                Receiver nodeReceiver = new Receiver(ip);
                nodeReceiver.start();
                /*If the node is serving on localhost, it is the first node in the
                chat app*/
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }

    private static IPAddress findAvailableIP( IPAddress ip ) throws Exception
    {
        while (true)
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
            }
            /*In this function, failure is good. When the function fails it means the
            ip is available for the node to use.*/
            catch ( Exception ex )
            {
                return ip;
            }
        }
    }
    /*Assumption: the highest ip on the local host subnet that can be accessed is
    127.0.10.250*/
    private static boolean checkSubnetMax( String ipString )
    {
        return ipString.equals("127.0.10.250");
    }

    private static void startSender()
    {

    }
}
