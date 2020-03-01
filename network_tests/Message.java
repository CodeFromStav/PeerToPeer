import java.io.*;
public class Message extends MessageTypes implements Serializable
{
    private String messageBody;
    private int messageCode;

    public Message(String mBody, int mCode)
    {
        switch(mCode)
        {
            case getJoinCode():
            case getJoinedCode():
            case getNoteCode():
            case getLeaveCode():
                break;
            default:
                break;
                return;
        }
        if (mCode != getJoinCode() || getJoinedCode() || getNoteCode() || getLeaveCode())
        {
            Sytem.out.println("Message Code does not refer to any message type...");
            return;
        }
        messageBody = mBody;
        messageCode = mCode;
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
