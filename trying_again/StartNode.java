public class StartNode
{
    public static void main(String[] args) throws Exception
    {
        Node newNode = new Node();
        newNode.startReceiver();
        /*Node starts sender with the "send" message code so that user can
        input messages into the console*/
        newNode.startSender();
    }
}
