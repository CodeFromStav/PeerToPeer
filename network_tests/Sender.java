import java.net.*;
import java.io.*;

public class Sender extends Node implements Runnable
{
    private int msgCode;
    private Socket inSocket;
    private Thread currentThread;

    public Sender( Socket inSocket, int msgCode )
    {
        this.msgCode = msgCode;
        this.inSocket = inSocket;
    }

    public void run()
    {

    }

    /* Creates a new thread to start a server-client connection */
    public void start()
    {
        if (currentThread == null)
        {
            currentThread = new Thread(this);
            currentThread.start();
        }
    }
}
