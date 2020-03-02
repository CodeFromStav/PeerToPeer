import java.io.*;
import java.net.*;
import java.util.*;

// Class necessary for sending messages to different Nodes
public class Sender extends Message implements Runnable
{
    // Boolean for breaking run() while() loop
    private boolean socketClosed = false;

    // Local private variables used within constructor
    private InetAddress IPAddress;
    private String username;
    private DatagramSocket socket;
    private int portNumber;

    // Constructor for Sender class
    Sender(InetAddress IPAddress, String username, DatagramSocket socket, int portNumber)
    {
        this.IPAddress = IPAddress;
        this.username = username;
        this.socket = socket;
        this.portNumber = portNumber;
    }

    // Run method necessary for classes that implement "Runnable"
    @Override
    public void run()
    {
        // Variables needed for constructing messages sent from Nodes
        String nodeMessage;
        Scanner messageScanner;
        String[] nodeMessageSplit;

        // Variable necessary for Message constructor
        String messageType;

        // Variables necessary for different parts of message contents
        String messageNote;
        String messageParticipantIP;
        InetAddress participantIP;

        // Continue to loop until socket is closed
        while(!socketClosed)
            try
            {
                // Scan in Node's message and store result
                // NOTE: Message must be separated by a ","!
                messageScanner = new Scanner(System.in);
                nodeMessage = messageScanner.nextLine();

                // Separate nodeMessage input in order to grab messageType
                nodeMessageSplit = nodeMessage.split(",");

                // Assigning variable to the node's IP Address
                messageType = nodeMessageSplit[0];

                // Create new Message object to handle each message from Node
                Message newMessage = new Message(messageType.toLowerCase(), nodeMessageSplit);

                // Switch to handle different message types
                //      Necessary since the contents of the messages vary
                //      Template of each message type is specified when used in switch statement
                switch(newMessage.getMessageCode())
                {
                    case (JOIN_CODE):

                        // Get and reassign contents
                        nodeMessageSplit = newMessage.getMessageBody();

                        // Since this a note message, its contents will be "<type>,<ipaddress>"
                        messageParticipantIP = nodeMessageSplit[1];

                        // Converting String object of IP address to InetAddress object
                        participantIP = InetAddress.getByName(messageParticipantIP);

                        // Storing participant Node to a temp for join message
                        Node participantNode = NodeInfo.findNodeEntry(participantIP);

                        // Create message to send to first node
                        nodeMessage = username + " wants to join the chat!";

                        // Convert message into byte form
                        byte[] bufferJoin = nodeMessage.getBytes();

                        System.out.println(NodeInfo.getArrayListSize());

                        // Creating a new Node with the information provided by the new node
                        Node newNode = new Node(IPAddress, username, socket, portNumber);

                        // Add an entry to the NodeInfo ArrayList<Node> for the new node
                        NodeInfo.createNodeEntry(newNode);

                        // Creating DatagramPacket to send to first node
                        DatagramPacket datagramJoin = new DatagramPacket(
                                bufferJoin,
                                bufferJoin.length,
                                participantNode.getIPAddress(),
                                participantNode.getPortNumber());

                        // Send Datagram to first node
                        participantNode.getDatagramSocket().send(datagramJoin);

                        // Purposely omit "break" statement here to force JOINED_CODE case...

                    case (JOINED_CODE):

                        // Create a "joined" message to send as confirmation of new member to each node
                        //      Since the newly added entry will have to be the most recently added
                        //      node, we can use the size of the NodeInfo ArrayList<Node> to grab
                        //      the node's position in the ArrayList
                        nodeMessage = NodeInfo.getUsername(NodeInfo.getArrayListSize() - 1) +
                                " has joined the chat!";

                        // Convert message into byte form
                        byte[] bufferJoined = nodeMessage.getBytes();

                        // Send message to all nodes in NodeInfo ArrayList<Node>
                        sendToAllNodes(bufferJoined);

                        break;

                    case (NOTE_CODE):

                        // Get and reassign contents
                        nodeMessageSplit = newMessage.getMessageBody();

                        // Since this a note message, its contents will be "<type>,<note>"
                        messageNote = nodeMessageSplit[1];

                        // Create message to send to each node
                        nodeMessage = username + ": " + messageNote;

                        // Convert message into byte form
                        byte[] bufferNote = nodeMessage.getBytes();

                        // Send message to all nodes in NodeInfo ArrayList<Node>
                        sendToAllNodes(bufferNote);

                        break;

                    case (LEAVE_CODE):

                        // Deleting Node from NodeInfo ArrayList<Node> based on port number
                        NodeInfo.deleteNodeEntry(portNumber);

                        // Create message to send to each node
                        nodeMessage = username + " is leaving the chat!";

                        // Convert message into byte form
                        byte[] bufferLeave = nodeMessage.getBytes();

                        // Send message to all nodes in NodeInfo ArrayList<Node>
                        sendToAllNodes(bufferLeave);

                        // Set socketClosed boolean to true to signify that the socket is closed
                        socketClosed = true;

                        // Close the socket
                        socket.close();
                        break;
                }
            }

            // Catch statement for when the while loop terminates because the socket closed
            catch (IOException e)
            {
                System.out.println("Socket closed!");
                e.printStackTrace();
            }
    }

    // Method necessary for sending a message to all of the nodes in ArrayList<Node>
    public void sendToAllNodes(byte[] buffer)
    {
        // Iterate through ArrayList<Node> so that each node receives the message
        for(int index = 0; index < NodeInfo.getArrayListSize(); index++)
        {
            try {
                // Creating DatagramPacket to send to each node
                DatagramPacket datagram = new DatagramPacket(
                    buffer,
                    buffer.length,
                    NodeInfo.getIPAddress(index),
                    NodeInfo.getPortNumber(index));

                // Send command for DatagramSockets
                NodeInfo.getDatagramSocket(index).send(datagram);
            }

            catch (IOException e)
            {
                System.out.println("Datagram doesn't exist!");
                e.printStackTrace();
            }
        }
    }
}