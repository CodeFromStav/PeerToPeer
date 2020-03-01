import java.io.*;
import java.net.*;
import java.util.*;

public class NodeInfo
{
    // Static ArrayList<Node> so that static methods can be used outside of NodeInfo.java
    private static ArrayList<Node> nodeInfoArrayList = new ArrayList();

    // Method for creating a new entry within the ArrayList<Node> with a Node
    void createNodeEntry(Node newNode)
    {
        nodeInfoArrayList.add(newNode);
    }

    // Getter method to return ArrayList<Node> size
    static int getArrayListSize()
    {
        return nodeInfoArrayList.size();
    }

    // Getter method to return ArrayList<Node> IPAddress of node at <nodeNumber>
    static InetAddress getIPAddress(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getIPAddress();
    }

    // Getter method to return ArrayList<Node> username of node at <nodeNumber>
    static String getUsername(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getUsername();
    }

    // Getter method to return ArrayList<Node> Datagram socket of node at <nodeNumber>
    static DatagramSocket getDatagramSocket(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getDatagramSocket();
    }

    // Getter method to return ArrayList<Node> port number of node at <nodeNumber>
    static int getPortNumber(int nodeNumber)
    {
        Node node = nodeInfoArrayList.get(nodeNumber);
        return node.getPortNumber();
    }
}