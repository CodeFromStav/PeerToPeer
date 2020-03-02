import java.net.*;
import java.io.*;

/* Test class to verify server-client connection functionality */
public class Node extends MessageTypes implements Serializable
{
    private String nodeID;
    private String nodeIP;
    private NodeInfo chatMesh;

    /*Returns IP that the node is able to use for its connection information
    and also opens up a socket connection with an available node on the network
    to retrieve its nodeInfo for future communications in the mesh*/
    public Node() throws Exception
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
                nodeID = assignID(ipString);
                /*Adds the first value to the ArrayList. Cool thing is,
                no matter how many nodes join the mesh, only one reference
                to a chatMesh will exist.*/
                chatMesh.update(this);
                return local;
            }
            /*If the server has already been started, the new node sends a message
            to the node it finds to grab its nodeInfo to store as its own, updates
            that node info with its own node information, and then sends the updated NodeInfo
            to every other node in the mesh.*/
            try
            {
                nodeSocket = new Socket( ipString, 8080 );
                /*check to see if the next ip in the subnet is available for the
                node to take*/
                ip.incrementIP();
                IPAddress availableIP = findAvailableIP(ip);
                nodeID = assignID(availableIP.toString());

                return availableIP;
            }
            catch ( Exception ex )
            {
                System.out.println( "IP-" + ipString + "-is not on network." );
                ip.incrementIP();
            }

        }
    }

    public void startReceiver() throws Exception
    {
        /*if server is not full...*/
        if ( this.nodeIP != null )
        {
            try
            {
                Receiver nodeReceiver = new Receiver(this);
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

    private IPAddress findAvailableIP( IPAddress ip ) throws Exception
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

                setChatMesh( testSocket );
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
    private boolean checkSubnetMax( String ipString )
    {
        return ipString.equals("127.0.10.250");
    }

    public void startSender(int msgCode, NodeInfo chatMesh)
    {
        Message joinMessage = new Message(this.chatMesh,"",msgCode);
        Sender nodeSender = new Sender( joinMessage, this );
        nodeSender.start();
    }
    private String assignID(String ipString)
    {
        return "Node-" + ipString.hashCode();
    }
    public void setIP(IPAddress ip) throws Exception
    {
        nodeIP = initializeNode(ip);
        System.out.println("This node's IP is ... " + nodeIP);
    }
    public IPAddress getIP()
    {
        return nodeIP;
    }
    public NodeInfo getChatMesh()
    {
        return chatMesh;
    }
    public void setChatMesh(Socket inSocket)
    {
        try
        {
            System.out.println("setChatMesh got called");

            ObjectOutputStream toNode = new ObjectOutputStream( inSocket.getOutputStream() );
            Message joinMessage = new Message(this,"",100);
            toNode.writeObject( joinMessage );
            System.out.println("yo");
            ObjectInputStream fromNode = new ObjectInputStream( inSocket.getInputStream() );
            Message currentMessage = (Message) fromNode.readObject();
            chatMesh = currentMessage.getNode().getChatMesh();
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
    }
    public void updateMesh(Node inNode)
    {
        chatMesh.update(inNode);
    }

}
