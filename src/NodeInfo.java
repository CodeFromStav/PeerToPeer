import java.net.*;
import java.util.*;

// Class necessary for keeping track of all Node info within an ArrayList<>
public class NodeInfo
{
    // Static ArrayList<Node> so that static methods can be used outside of NodeInfo.java
    private static ArrayList<Node> nodeInfoArrayList = new ArrayList();

    // Method for creating a new entry within the ArrayList<Node> with a Node
    static void createNodeEntry(Node newNode)
    {
        nodeInfoArrayList.add(newNode);
    }

    // Method for deleting a Node entry within the ArrayList<Node>
    static void deleteNodeEntry(int portNumber)
    {
        // Iterate through ArrayList<Node> to find Node that is leaving
        for(int index = 0; index < NodeInfo.getArrayListSize(); index++)
        {
            // Store current node
            Node node = nodeInfoArrayList.get(index);

            // Comparing Port numbers to find the matching Node
            if(portNumber == node.getPortNumber())
            {
                nodeInfoArrayList.remove(index);
            }
        }
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