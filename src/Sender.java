import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable
{
    private InetAddress IPAddress;
    private String username;
    private DatagramSocket socket;
    private int portNumber;

    Sender(InetAddress IPAddress, String username, DatagramSocket socket, int portNumber)
    {
        this.IPAddress = IPAddress;
        this.username = username;
        this.socket = socket;
        this.portNumber = portNumber;
    }

    @Override
    public void run()
    {
        while(true)

            try {
                String message;
                Scanner sc = new Scanner(System.in);

                message = sc.nextLine();
                if(message.equalsIgnoreCase("exit"))
                {
                    socket.close();
                    break;
                }
                message = username + ": " + message;
                byte[] buffer = message.getBytes();

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