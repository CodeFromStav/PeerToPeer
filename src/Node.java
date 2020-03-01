import java.net.*;
import java.io.*;
import java.util.*;

public class Node
{
    // Static int to keep consistent record of number of nodes for NodeInfo ArrayList<Node>
    static volatile int nodeNumber = 1;

    // Static NodeInfo object that can be updated real time
    static volatile NodeInfo nodeInfo = null;

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
    public static void main(String[] args) throws IOException {

        Scanner userInput;
        String userHostname;
        InetAddress userIP;
        String username;
        int portNumber;
        String firstParticipant;

        // Ask user for their Hostname from terminal/cmd
        //      For all OS's: Open terminal/cmd and type "hostname" and press enter.
        //      The resulting output will be your computer's hostname
        System.out.print("Please enter your hostname: ");

        // Scan in user's Hostname and store result
        userInput = new Scanner(System.in);
        userHostname = userInput.nextLine();

        // Grab user's IP using their Hostname
        userIP = InetAddress.getByName(userHostname);

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
            nodeInfo.createNodeEntry(newNode);
        }
        else
        {
            // Create new Node object with data supplied by user
            Node newNode = new Node(userIP, username, nodeSocket, portNumber);
        }

        // Node creation confirmation
        System.out.println("\nYour node has been created!");

        // Layout for 3 types of message: Join, Leave, Note
        //      Join message is required for new nodes
        System.out.println("Here is the format for each type of message (entered without quotes):" +
                        "\n    Join: 'join'\n    Leave: 'exit'\n    Note: 'note,<yournote>'");

        // Create a new Receiver Thread object using user's IP, socket and port number
        Thread receiverThread = new Thread(new Receiver(userIP, nodeSocket, portNumber));

        // Spawn a thread for reading messages
        receiverThread.start();

        // Create a new Sender Thread object using user's IP, username, Socket and port#
        Thread senderThread = new Thread(new Sender(userIP, username, nodeSocket, portNumber));

        // Spawn a thread for reading messages
        senderThread.start();

        // Let the user know that they can start sending messages
        System.out.println("\nYou can start typing your messages...\n");
    }
}