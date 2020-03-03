import java.io.*;
public class Message implements Serializable
{
    private String messageBody;
    private int messageCode;
    private NodeInfo inNodeInfo;
    private String[] nodeInfo;

    //initializes message
    public Message ( NodeInfo inNodeInfo, String[] nodeInfo, int messageCode, String messageBody)
    {
        this.nodeInfo = nodeInfo;
        this.inNodeInfo = inNodeInfo;
        this.messageBody = messageBody;
        this.messageCode = messageCode;
    }
    //gets body of message
    public String getMsg( )
    {
        return nodeInfo[0] + " says: " + messageBody;
    }
    //gets messageCode
    public int getCode( )
    {
        return messageCode;
    }
    //gets NodeInfo
    public NodeInfo getNodeInfo( )
    {
        return inNodeInfo;
    }
    //gets CurrentNode
    public String[] getCurrentNode( )
    {
        return nodeInfo;
    }
}
