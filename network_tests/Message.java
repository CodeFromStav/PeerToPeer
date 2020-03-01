import java.io.*;
public class Message extends MessageTypes implements Serializable
{
    private String messageBody;
    private int messageCode;
    private String nodeID;

    public Message(String nodeID, String mBody, int mCode)
    {
        switch(mCode)
        {
            case JOIN_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.nodeID = nodeID;
                break;
            case JOINED_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.nodeID = nodeID;
                break;
            case NOTE_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.nodeID = nodeID;
                break;
            case LEAVE_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.nodeID = nodeID;
                break;
            default:
                throw new IllegalArgumentException("Message code does not exist...");
        }
    }
    public String getMsg()
    {
        return messageBody;
    }
    public int getCode()
    {
        return messageCode;
    }

}
