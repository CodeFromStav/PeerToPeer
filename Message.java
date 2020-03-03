import java.io.*;

// Message abstraction
public class Message implements Serializable
{
    private String messageBody;
    private int messageCode;
    private NodeInfo inNodeInfo;
    private String[] nodeInfo;

    // Initializes Message
    public Message ( NodeInfo inNodeInfo, String[] nodeInfo, int messageCode, String messageBody )
    {
        this.nodeInfo = nodeInfo;
        this.inNodeInfo = inNodeInfo;
        this.messageBody = messageBody;
        this.messageCode = messageCode;
    }
    // Gets body of message
    public String getMsg()
    {
        return nodeInfo[0] + " says: " + messageBody;
    }
    // Gets messageCode
    public int getCode()
    {
        return messageCode;
    }
    // Gets NodeInfo
    public NodeInfo getNodeInfo()
    {
        return inNodeInfo;
    }
    // Gets CurrentNode
    public String[] getCurrentNode()
    {
        return nodeInfo;
    }
}
