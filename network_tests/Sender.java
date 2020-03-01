import java.net.*;
import java.io.*;

public class Sender extends MessageTypes implements Runnable
{
    private Thread currentThread;
    private int msgCode;
    private Message currentMessage;
    private NodeInfo chatMesh;
    public Sender(int msgCode)
    {
        this.msgCode = msgCode;
    }

    public void run()
    {
        while (currentMessage.getCode() != LEAVE_CODE)
        {
            switch (msgCode)
            {
                case JOIN_CODE:
                    break;
                case JOINED_CODE:
                    break;
                case NOTE_CODE:
                    break;
                case LEAVE_CODE:
                    break;
            }
        }

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
