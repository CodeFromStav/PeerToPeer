import java.io.*;
public class Message extends MessageTypes implements Serializable
{
    private String messageBody;
    private int messageCode;
    private String nodeID;
    private IPAddress nodeIP;
    private Node currentNode;

    public Message(Node inNode, String mBody, int mCode)
    {
        switch(mCode)
        {
            case JOIN_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.currentNode = inNode;
                break;
            case JOINED_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.currentNode = inNode;
                break;
            case NOTE_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.currentNode = inNode;
                break;
            case LEAVE_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.currentNode = inNode;
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
    public Node getNode()
    {
        return currentNode;
    }
}
