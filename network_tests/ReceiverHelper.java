import java.net.*;
import java.io.*;
public class ReceiverHelper implements Runnable
{
    Thread currentThread;
    Socket currentSocket;
    Message currentMessage;
    ReceiverHelper( Socket inSocket )
    {
        currentSocket = inSocket;
    }
    public void run()
    {
        try
        {
            ObjectInputStream fromNode = new ObjectInputStream( currentSocket.getInputStream() );
            /*
            * Implementation of the switch statement of message codes. object input stream should
            * be able to grab the message class's getCode() method to find out which type of message it is
            *
            *
            *
            */
            fromNode.close();
        }
        catch ( IOException ex )
        {
            //System.out.println( ex );
        }
    }

    /* Creates a new thread to start a server-client connection */
    public void start()
    {
        if (currentThread == null)
        {
            currentThread = new Thread( this );
            currentThread.start();
        }
    }
}
