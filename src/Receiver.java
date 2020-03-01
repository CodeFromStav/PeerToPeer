import java.net.*;
import java.io.*;

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

    @Override
    public void run()
    {
        while(true)
        {
            byte[] buffer = new byte[Receiver.MAX_LEN];

            DatagramPacket datagram = new DatagramPacket(buffer,buffer.length, IPAddress, portNumber);

            String message;

            try
            {
                socket.receive(datagram);
                message = new String(buffer,0,datagram.getLength(),"UTF-8");

                System.out.println(message);
            }
            catch(IOException e)
            {
                System.out.println("Socket closed!");
            }
        }
    }
}