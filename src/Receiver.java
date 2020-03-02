import java.net.*;
import java.io.*;

// Class necessary for printing out received messages to terminal/cmd
public class Receiver implements Runnable
{
    // Static int that determines the max length of a buffer
    private static final int MAX_LEN = 1000;

    // Local private variables used within constructor
    private InetAddress IPAddress;
    private DatagramSocket socket;
    private int portNumber;

    // Constructor for Receiver class
    Receiver(InetAddress IPAddress, DatagramSocket socket, int portNumber)
    {
        this.IPAddress = IPAddress;
        this.socket = socket;
        this.portNumber = portNumber;
    }

    // Run method necessary for classes that implement "Runnable"
    @Override
    public void run()
    {
        String receivedMessage;

        // Continue to loop until break is hit in the catch block
        while(true)
        {

            // Create buffer of MAX_LEN
            byte[] bufferReceiver = new byte[Receiver.MAX_LEN];

            // Creating a DatagramPacket to send back to the Node
            DatagramPacket datagramReceived = new DatagramPacket(bufferReceiver, bufferReceiver.length,
                    IPAddress, portNumber);

            try
            {
                // Getting Datagram sent by socket
                socket.receive(datagramReceived);

                // Convert the Datagram object into a String object
                receivedMessage = new String(bufferReceiver, 0, datagramReceived.getLength(),
                        "UTF-8");

                // Print the received message to the screen
                System.out.println(receivedMessage);
            }

            // Catch statement for when the while loop terminates because the socket closed
            catch(IOException e)
            {
                System.out.println("You have left the chat; socket closed!");
                break;
            }
        }
    }
}