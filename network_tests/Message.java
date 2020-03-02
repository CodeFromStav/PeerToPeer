import java.io.*;
public class Message extends Node implements Serializable
{
    private String messageBody;
    private int messageCode;
    private String nodeID;
    private IPAddress nodeIP;
    private NodeInfo inNodeInfo;

    public Message (NodeInfo inNodeInfo, String mBody, int mCode)
    {
        switch(mCode)
        {
            case JOIN_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.inNodeInfo = inNodeInfo;
                break;
            case JOINED_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.inNodeInfo = inNodeInfo;
                break;
            case NOTE_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.inNodeInfo = inNodeInfo;
                break;
            case LEAVE_CODE:
                messageBody = mBody;
                messageCode = mCode;
                this.inNodeInfo = inNodeInfo;
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
    public NodeInfo getNodeInfo()
    {
        return NodeInfo;
    }
}