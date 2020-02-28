import java.net.*;
import java.io.*;
public class ReceiverHelper implements Runnable {
    Thread currentThread;
    Socket currentSocket;
    ReceiverHelper(Socket inSocket)
    {
        currentSocket = inSocket;
    }
    public void run() {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(currentSocket.getInputStream());
            System.out.println("Hey there you little fuck");
            fromClient.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /* Creates a new thread to start a server-client connection */
    public void start() {
        if (currentThread == null) {
            currentThread = new Thread(this);
            currentThread.start();
        }
    }
}
