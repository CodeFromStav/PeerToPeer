import java.io.*;
import java.net.*;
import java.util.*;

public class NodeInfo
{
    private InetAddress IPAddress;
    private String username;
    private DatagramSocket socket;
    private int portNumber;

    private ArrayList<Node> nodeInfoArrayList = new ArrayList();

    void createNodeEntry(Node newNode)
    {
        nodeInfoArrayList.add(newNode);
    }

    InetAddress getIPAddress(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getIPAddress();
    }

    String getUsername(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getUsername();
    }
}