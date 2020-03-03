import java.net.*;

// Class necessary for creating Node Objects
public class Node
{
    // Static NodeInfo object that can be updated real time
    private NodeInfo nodeInfo;

    // Information/variables necessary to create a Node object
    private InetAddress IPNumber;
    private String userName;
    private int portNumber;

    // Constructor for Node object
    Node( String userName, InetAddress IPAddress, int portNumber )
    {
        this.IPNumber = IPAddress;
        this.userName = userName;
        this.portNumber = portNumber;
        nodeInfo = new NodeInfo();
    }

    // Returns current information
    public String[] getCurrentNode()
    {
        String intStr = "" + portNumber;
        return new String[] {userName, IPNumber.getHostAddress(), intStr};
    }

    // Updates mesh
    public void updateMesh( NodeInfo inNodeInfo )
    {
        nodeInfo = inNodeInfo;
    }

    // Listens for incoming socket connections
    public void startReceiver()
    {
        Receiver newReceiver = new Receiver( nodeInfo, getCurrentNode(), this );
        newReceiver.start();
    }

    // Returns NodeInfo
    public NodeInfo getNodeInfo()
    {
        return nodeInfo;
    }

    // Add node data to NodeInfo
    public void addNodeData( String[] inData )
    {
        nodeInfo.update( inData );
    }

    // Starts a Sender object
    public void startSender()
    {
        Sender createSender = new Sender( nodeInfo, getCurrentNode(), this );
    }

    // Remove node from NodeInfo
    public void removeNode( int inPort )
    {
        nodeInfo.remove( inPort );
    }
}
