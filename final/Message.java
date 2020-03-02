import java.io.*;
public class Message implements Serializable
{
    private String messageBody;
    private int messageCode;
    private NodeInfo inNodeInfo;
    private String[] nodeInfo;

    public Message (NodeInfo inNodeInfo, String[] nodeInfo, int messageCode, String messageBody)
    {
        this.nodeInfo = nodeInfo;
        this.inNodeInfo = inNodeInfo;
        this.messageBody = messageBody;
        this.messageCode = messageCode;
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
