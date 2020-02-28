public class StartNode
{
    public static void main(String[] args) throws Exception
    {
        Node newNode = new Node();
        IPAddress ip = new IPAddress( "127.0.0.1" );
        newNode.setIP(ip);
        startReceiver(nodeIP);
        /*Node starts sender with the "send" message code*/
        startSender(null, 110);
    }
}
