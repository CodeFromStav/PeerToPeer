import java.io.*;
public class Message extends Node implements Serializable
{
    private String messageBody;
    private int messageCode;
    private NodeInfo inNodeInfo;
    private String[] nodeInfo;

    public Message (NodeInfo inNodeInfo, String[] nodeInfo, int messageCode, String messageBody) throws Exception
    {
        switch(messageCode)
        {
            case JOIN_CODE:
                this.nodeInfo = nodeInfo;
                this.inNodeInfo = inNodeInfo;
                this.messageBody = messageBody;
                this.messageCode = messageCode;
                break;
            case JOINED_CODE:
                this.nodeInfo = nodeInfo;
                this.inNodeInfo = inNodeInfo;
                this.messageBody = messageBody;
                this.messageCode = messageCode;
                break;
            case NOTE_CODE:
                this.nodeInfo = nodeInfo;
                this.inNodeInfo = inNodeInfo;
                this.messageBody = messageBody;
                this.messageCode = messageCode;
                break;
            case LEAVE_CODE:
                this.nodeInfo = nodeInfo;
                this.inNodeInfo = inNodeInfo;
                this.messageBody = messageBody;
                this.messageCode = messageCode;
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
        return inNodeInfo;
    }
    public String[] getCurrentNode()
    {
        return nodeInfo;
    }
}
