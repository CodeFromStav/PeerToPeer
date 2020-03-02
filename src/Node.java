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
    static volatile NodeInfo nodeInfo = null;

    // Information/variables necessary to create a Node object
    private InetAddress IPAddress;
    private String username;
    private DatagramSocket socket;
    private int portNumber;

    // Constructor for Node object
    Node(InetAddress IPAddress, String username, DatagramSocket socket, int portNumber)
    {
        this.IPAddress = IPAddress;
        this.username = username;
        this.socket = socket;
        this.portNumber = portNumber;
    }

    // Getter method to return IPAddress of Node
    InetAddress getIPAddress()
    {
        return this.IPAddress;
    }

    // Getter method to return username of Node
    String getUsername()
    {
        return this.username;
    }

    // Getter method to return Socket of Node
    DatagramSocket getDatagramSocket()
    {
        return this.socket;
    }

    // Getter method to return port number of Node
    int getPortNumber()
    {
        return this.portNumber;
    }

    // Main; driver for project
    public static void main(String[] args) {

        // Variables necessary for gathering user input
        Scanner userInput;
        InetAddress userAddress;
        InetAddress userIP;
        String[] userAddressSplit;
        String username;
        int portNumber;
        String firstParticipant;

        try
        {
            // Letting user know of IP Address retrieval
            System.out.println("Retrieving IP Address...");

            // Get the user's Hostname/IP address
            userAddress = InetAddress.getLocalHost();

            // Split userAddress for IP Address
            userAddressSplit = userAddress.toString().split("/");

            // Converting String object of IP address to InetAddress object
            userIP = InetAddress.getByName(userAddressSplit[1]);

            // Printing out user's IP Address to the screen
            System.out.println("IP Address found! Your IP Address: " + userAddressSplit[1]);

            // Ask user for their username from terminal/cmd
            System.out.print("Please enter your username: ");

            // Scan in user's username and store result
            userInput = new Scanner(System.in);
            username = userInput.nextLine();

            // Ask user for their port number from terminal/cmd
            System.out.print("Please enter your port number: ");

            // Scan in user's port number and store result
            userInput = new Scanner(System.in);
            portNumber = userInput.nextInt();

            // Create a new Datagram socket for node
            DatagramSocket nodeSocket = new DatagramSocket(portNumber, userIP);

            // Ask user for if they're the first user in this chat session
            System.out.print("Are you the first participant? (yes/no) ");

            userInput = new Scanner(System.in);
            firstParticipant = userInput.nextLine();

            // If this is the first node in the chat session
            if(firstParticipant.equalsIgnoreCase("yes"))
            {
                // Create new Node object with data supplied by user
                Node newNode = new Node(userIP, username, nodeSocket, portNumber);

                // Create new NodeInfo object
                nodeInfo = new NodeInfo();

                // Add Node to NodeInfo class (ArrayList<Node>)
                NodeInfo.createNodeEntry(newNode);
            }

            // Node creation confirmation
            System.out.println("Your node has been created!");

            // Layout for 3 types of message: Join, Leave, Note
            //      Join message is required for new nodes
            System.out.println("\nHere is the format for each type of message (entered without quotes):" +
                    "\n    Join: 'join,<ipaddressofparticipantnode>'\n    Leave: 'leave'\n    " +
                    "Note: 'note,<yournote>'");

            // Create a new Receiver Thread object using user's IP, socket and port number
            Thread receiverThread = new Thread(new Receiver(userIP, nodeSocket, portNumber));

            // Warning to new nodes to join first
            // Note: this is not enforced! Assume user is not a monkey and follows the directions
            System.out.println("\nNew users need to 'join' first!");

            // Spawn a thread for reading messages
            receiverThread.start();

            // Create a new Sender Thread object using user's IP, username, Socket and port#
            Thread senderThread = new Thread(new Sender(userIP, username, nodeSocket, portNumber));

            // Spawn a thread for sending messages
            senderThread.start();

            // Let the user know that they can start sending messages
            System.out.println("\nYou can start typing your messages...\n");
        }

        catch (IOException e)
        {
            System.out.println("Failed to create Datagram Socket!");
            e.printStackTrace();
        }
    }
}