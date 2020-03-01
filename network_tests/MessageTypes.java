public class MessageTypes
{
    //Initialize codes with arbritrary values
    private int JOIN_CODE = 100;
    private int JOINED_CODE = 101;
    private int LEAVE_CODE = 105;
    private int NOTE_CODE = 110;

    /*get join code*/
    public int getJoinCode()
    {
        return JOIN_CODE;
    }
    /*get Joined code*/
    public int getJoinedCode()
    {
        return JOINED_CODE;
    }
    /*get Leave code*/
    public int getLeaveCode()
    {
        return LEAVE_CODE;
    }
    /*get Note code*/
    public int getNoteCode()
    {
        return NOTE_CODE;
    }
}
