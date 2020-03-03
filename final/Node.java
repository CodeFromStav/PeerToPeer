/*
Authors: Matthew Amato-Yarbrough
         Brigham Ray
         Stavros Triantis
Date: Spring 2020

References:

https://docs.oracle.com/javase/7/docs/api/java/net/DatagramPacket.html
https://docs.oracle.com/javase/7/docs/api/java/net/DatagramSocket.html
https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html
https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
https://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html
 */

import java.net.*;
import java.io.*;
import java.util.*;

// Class necessary for creating Node Objects
//      Also houses main() method
public class Node
{
    // Static NodeInfo object that can be updated real time
    private NodeInfo nodeInfo;

    // Information/variables necessary to create a Node object
    private InetAddress IPNumber;
    private String userName;
    private int portNumber;

    // Constructor for Node object
    Node( String userName, InetAddress IPAddress, int portNumber)
    {
        this.IPNumber = IPAddress;
        this.userName = userName;
        this.portNumber = portNumber;
        nodeInfo = new NodeInfo();
    }

    // Getter method to return IPAddress of Node
    InetAddress getIPAddress()
    {
        return this.IPNumber;
    }

    // Getter method to return userName of Node
    String getuserName()
    {
        return this.userName;
    }

    // Getter method to return port number of Node
    int getPortNumber()
    {
        return this.portNumber;
    }

    // Main; driver for project

    public String[] getCurrentNode()
    {
        String intStr = "" + portNumber;
        return new String[] {userName, IPNumber.getHostAddress(), intStr};
    }

    public void updateMesh(NodeInfo inNodeInfo)
    {
        nodeInfo = inNodeInfo;
    }

    public void startReceiver()
    {
        Receiver newReceiver = new Receiver(nodeInfo, getCurrentNode());
        newReceiver.start();

    }
    public NodeInfo getNodeInfo()
    {
        return nodeInfo;
    }
    public void addNodeData(String[] inData)
    {
        nodeInfo.update(inData);
    }
    public String nodeInfoToString()
    {
        String infoToString = " ";
        for (int i = 0; i < nodeInfo.getSize(); i++)
        {
            infoToString += "{";
            for (int j = 0; j < 3; j++)
            {
                switch(j)
                {
                    case 0:
                        infoToString += "id: " + nodeInfo.get(i)[j] + ", ";
                        break;
                    case 1:
                        infoToString += "ip: " + nodeInfo.get(i)[j]  + ", ";
                        break;
                    case 2:
                        infoToString += "port: " + nodeInfo.get(i)[j]  + "";
                        break;
                }
            }
            infoToString += "}";
        }
        return "Current NodeInfo is " + infoToString;
    }
}
