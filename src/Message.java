import java.io.*;

// Class necessary for creating message based on "type" input by node
public class Message extends MessageTypes implements Serializable
{
    private String messageType;
    private String[] messageContents;
    private int messageCode;

    // Constructor for Message class
    Message(String messageType, String[] entireMessageSplit)
    {
        // Switch statement to handle each Message type
        //      Assigns the message body and message code to Message object
        //      If message type doesn't exist, throw an exception for type not found
        switch(messageType)
        {
            case ("join"):
                this.messageContents = entireMessageSplit;
                messageCode = super.JOIN_CODE;
                break;
            case ("joined"):
                this.messageContents = entireMessageSplit;
                messageCode = super.JOINED_CODE;
                break;
            case ("note"):
                this.messageContents = entireMessageSplit;
                messageCode = super.NOTE_CODE;
                break;
            case ("leave"):
                this.messageContents = entireMessageSplit;
                messageCode = super.LEAVE_CODE;
                break;
            default:
                throw new IllegalArgumentException("Message type does not exist...");
        }
    }

    // Empty constructor
    public Message() { }

    // Getter method for returning the message code
    int getMessageCode()
    {
        return this.messageCode;
    }

    // Getter method for returning the contents of the entire message
    String[] getMessageBody()
    {
        return this.messageContents;
    }

}