import java.net.*;
import java.io.*;

/* Test class to verify server-client connection functionality */
public class Node extends MessageTypes
{
    private String nodeID;
    private String nodeIP;
    private NodeInfo chatMesh;

    public Node() throws Exception
    {
        chatMesh = new NodeInfo();
        IPAddress ip = new IPAddress("127.0.0.1");
        int counter = 0;
        //while (counter != 4)
        while (true)
        {
            String ipString = ip.toString();
            if ( checkSubnetMax( ipString ) )
            {
                ipString = "127.0.0.1";
                nodeID = assignID( ipString );
                this.nodeIP = ipString;
                System.out.println( "server is down, initializing node " + nodeID + "on IP... " + ipString );
                return;
            }
            try
            {
                Socket nodeSocket = new Socket( ipString, 8080 );
                ip.incrementIP();
                String availableIP = findAvailableIP( ip );
                nodeID = assignID(availableIP);

                this.nodeIP = availableIP;
                System.out.println( "server is down, initializing node " + nodeID + "on IP... " + ipString );
                return;
            }
            catch ( Exception ex )
            {
                System.out.println( "IP-" + ipString + "-is not on network." );
                ip.incrementIP();
            }
            counter++;

        }
    }

    public void startReceiver() throws Exception
    {
        try
        {
            Receiver nodeReceiver = new Receiver( chatMesh,getCurrentNode() );
            nodeReceiver.start();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    private String findAvailableIP( IPAddress ip ) throws Exception
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
                is checked to see if it is available. The socket it successfully creates retreives
                an updated chatMesh from the already connected node.*/
                Socket testSocket = new Socket( ipString,8080 );
                System.out.println( "Socket opened....");

                //setChatMesh( testSocket );
                testSocket.close();
                ip.incrementIP();
            }
            /*In this function, failure is good. When the function fails it means the
            ip is available for the node to use.*/
            catch ( Exception ex )
            {
                return ipString;
            }
        }
    }
    /*Assumption: the highest ip on the local host subnet that can be accessed is
    127.0.10.250*/
    private boolean checkSubnetMax( String ipString )
    {
        return ipString.equals("127.0.10.250");
    }

    public void startSender()
    {
        Sender nodeSender = new Sender(chatMesh,getCurrentNode());
        nodeSender.start();
    }
    private String assignID(String ipString)
    {
        return "Node-" + ipString.hashCode();
    }
    public NodeInfo getChatMesh()
    {
        return chatMesh;
    }
    public void setChatMesh(Socket inSocket) throws Exception
    {
        try
        {
            System.out.println("setChatMesh got called");
            ObjectOutputStream toNode = new ObjectOutputStream( inSocket.getOutputStream() );
            Message joinMessage = new Message(chatMesh,getCurrentNode(),100,"");
            toNode.writeObject( joinMessage );
            System.out.println("yo");
            ObjectInputStream fromNode = new ObjectInputStream( inSocket.getInputStream() );
            Message currentMessage = (Message) fromNode.readObject();
            chatMesh = currentMessage.getNodeInfo();
            System.out.println("Chat mesh is... " + chatMesh.getSize() + "Nodes Deep");
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
        catch ( ClassNotFoundException ex )
        {
            System.out.println("CNFException...");
        }
        catch ( IllegalArgumentException ex )
        {
            ex.printStackTrace();
        }
    }
    public String[] getCurrentNode()
    {
        return new String[] {nodeID,nodeIP};
    }

}
