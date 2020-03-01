import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable
{
    private MulticastSocket socket;
    private InetAddress group;
    private int port;

    Sender(MulticastSocket socket,InetAddress group,int port)
    {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    @Override
    public void run()
    {
        while(true)

            try {
                String message;
                Scanner sc = new Scanner(System.in);

                message = sc.nextLine();
                if(message.equalsIgnoreCase(Node.TERMINATE))
                {
                    Node.finished = true;
                    socket.leaveGroup(group);
                    socket.close();
                    break;
                }
                message = Node.name + ": " + message;
                byte[] buffer = message.getBytes();
                DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
                socket.send(datagram);

            }

            catch (IOException e)
            {
                System.out.println("Socket closed!");
                e.printStackTrace();
            }
    }
}