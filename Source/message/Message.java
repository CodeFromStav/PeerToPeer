package message;

//Contain informaton for what type of message gets set over network
//Each code corresponds to message

public class Message extends MessageTypes
{
    public static void message( int ChatNode )
    {

    }

    public int getType()
    {
        if( )

    }
    //shouldn't this be type string, not void?
    public String setContent( String messageCode)
    {
        String outputStr;
        switch( messageCode )
        {
            case 1: messageCode = 100;
            outputStr = nodeName + " would like to join.";
            break;

            case 2: messageCode = 101;
            outputStr = nodeName + " has joined the chat.";
            break;

            case 3: messageCode = 105;
            outputStr = nodeName + " has left the chat.";
            break;
            
            //this one pry isn't right. We must insert the message they input
            case 4: messageCode = 110;
            outputStr = nodeName + " message: " + message( ChatNode );
            break;


        }


        if ( message == 100 )
        {
            //broadcast join req message
        }
        else if( message == 101 )
        {
            //broadcast message joined
        }
        else if( message = 105 )
        {
            //broadcast leave message
        }
        else if( message = 110 )
        {

        }

    }

    public String getContent()
    {

    }


}