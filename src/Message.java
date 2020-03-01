import java.io.*;

// Class necessary for creating message based on "type" input by node
public class Message extends MessageTypes implements Serializable
{
    private String messageType;
    private String[] messageBody;
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
                this.messageBody = entireMessageSplit;
                messageCode = super.JOIN_CODE;
                break;
            case ("joined"):
                this.messageBody = entireMessageSplit;
                messageCode = super.JOINED_CODE;
                break;
            case ("note"):
                this.messageBody = entireMessageSplit;
                messageCode = super.NOTE_CODE;
                break;
            case ("leave"):
                this.messageBody = entireMessageSplit;
                messageCode = super.LEAVE_CODE;
                break;
            default:
                throw new IllegalArgumentException("Message type does not exist...");
        }
    }

    // Empty constructor
    public Message() { }
}