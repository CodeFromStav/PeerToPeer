import java.io.*;
import java.net.*;
import java.util.*;

public class NodeInfo
{
    private static ArrayList<Node> nodeInfoArrayList = new ArrayList();

    private InetAddress IPAddress;
    private String username;
    private DatagramSocket socket;
    private int portNumber;


    // method for creating a new entry within the ArrayList<Node> with a Node
    void createNodeEntry(Node newNode)
    {
        nodeInfoArrayList.add(newNode);
    }

    // getter
    static int getArrayListSize()
    {
        return nodeInfoArrayList.size();
    }

    static InetAddress getIPAddress(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getIPAddress();
    }

    static String getUsername(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getUsername();
    }

    static DatagramSocket getDatagramSocket(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getDatagramSocket();
    }

    static int getPortNumber(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getPortNumber();
    }
}