import java.io.*;
import java.net.*;
import java.util.*;

// Class necessary for sending messages to different Nodes
public class Sender extends Message implements Runnable
{
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
        String nodeMessage;
        Scanner messageScanner;
        String[] nodeMessageSplit;

        // Variable necessary for Message constructor
        String messageType;

        // Variables necessary for different parts of message contents
        InetAddress messageIP;
        String messageUsername;
        DatagramSocket messageDatagramSocket;
        int messagePortNumber;
        String messageNote;

        while(true)
            try
            {
                // Scan in Node's message and store result
                // NOTE: Message must be separated by a ","!
                messageScanner = new Scanner(System.in);
                nodeMessage = messageScanner.nextLine();

                // Seperate nodeMessage input which is <hostname>/<ipaddress>
                nodeMessageSplit = nodeMessage.split(",");

                // Assigning variable to the node's IP Address
                messageType = nodeMessageSplit[0];

                // Create new Message object to handle each message from Node
                Message newMessage = new Message(messageType.toLowerCase(), nodeMessageSplit);

                // Switch to handle different message types
                //      Necessary since the contents of the messages vary
                //      Template of each message type is specified in switch statement
                switch(newMessage.getMessageCode())
                {
                    case (JOIN_CODE):
                        break;

                    case (JOINED_CODE):
                        break;

                    case (NOTE_CODE):

                        // Get and reassign contents
                        nodeMessageSplit = newMessage.getMessageBody();

                        // Since this a note message, its contents will be "<type>,<note>"
                        messageNote = nodeMessageSplit[1];

                        // Create message to send to each node
                        nodeMessage = username + ": " + messageNote;

                        // Convert message into byte form
                        byte[] buffer = nodeMessage.getBytes();

                        // Iterate through ArrayList<Node> so that each node receives the message
                        for(int index = 1; index <= NodeInfo.getArrayListSize(); index++)
                        {
                            // Creating DatagramPacket to send to each node
                            DatagramPacket datagram = new DatagramPacket(
                                    buffer,
                                    buffer.length,
                                    NodeInfo.getIPAddress(index-1),
                                    NodeInfo.getPortNumber(index-1));

                            // Send command for DatagramSockets
                            NodeInfo.getDatagramSocket(index-1).send(datagram);
                        }
                        break;

                    case (LEAVE_CODE):
                        socket.close();
                        break;
                }
            }

            catch (IOException e)
            {
                System.out.println("Socket closed!");
                e.printStackTrace();
            }
    }
}