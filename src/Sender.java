import java.io.*;
import java.net.*;
import java.util.*;

// Class necessary for sending messages to different Nodes
public class Sender implements Runnable
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
        String messageType;

        while(true)

            try
            {
                // Scan in Node's message and store result
                // NOTE: Message must be separated by a ","!
                messageScanner = new Scanner(System.in);
                nodeMessage = messageScanner.nextLine();

                // Seperate nodeMessage input which is <hostname>/<ipaddress>
                nodeMessageSplit = nodeMessage.split(",");

                // Assigning to the user's IP Address
                messageType = nodeMessageSplit[0];

                if(nodeMessage.equalsIgnoreCase("exit"))
                {
                    socket.close();
                    break;
                }
                nodeMessage = username + ": " + nodeMessage;
                byte[] buffer = nodeMessage.getBytes();

                for(int index = 1; index <= NodeInfo.getArrayListSize(); index++)
                {
                    System.out.println(NodeInfo.getArrayListSize());

                    DatagramPacket datagram = new DatagramPacket(
                            buffer,
                            buffer.length,
                            NodeInfo.getIPAddress(index-1),
                            NodeInfo.getPortNumber(index-1));

                    socket.send(datagram);
                }
            }

            catch (IOException e)
            {
                System.out.println("Socket closed!");
                e.printStackTrace();
            }
    }
}