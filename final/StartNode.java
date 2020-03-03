import java.io.*;
import java.net.*;
import java.util.*;

public class StartNode
{
    public static void main(String[] args) {

        // Variables necessary for gathering user input
        Scanner userInput;
        InetAddress userAddress;
        InetAddress userIP;
        String[] userAddressSplit;
        String userName;
        int portNumber;
        String firstParticipant;
        Node newNode;

        try
        {
            String inPort = args[0];
            // Letting user know of IP Address retrieval
            System.out.println("Retrieving IP Address...");

            // Get the user's Hostname/IP address
            if (args[0] == null)
            {
                userAddress = InetAddress.getLocalHost();
                // Split userAddress for IP Address
                userAddressSplit = userAddress.toString().split("/");

                // Converting String object of IP address to InetAddress object
                userIP = InetAddress.getByName(userAddressSplit[1]);

            }
            else
            {
                userIP = InetAddress.getByName(args[0]);
            }
            //userAddress = InetAddress.getLocalHost();



            // Printing out user's IP Address to the screen
            //System.out.println("IP Address found! Your IP Address: " +
                //userAddressSplit[1]);
            //userAddress = InetAddress.getByName("127.0.0.1");
            //userIP = InetAddress.getByName("127.0.0.1");

            // Ask user for their userName from terminal/cmd
            System.out.print("Please enter your userName: ");

            // Scan in user's userName and store result
            userInput = new Scanner( System.in );
            userName = userInput.nextLine();

            // Ask user for their port number from terminal/cmd
            //System.out.print("Please enter your port number: ");

            // Scan in user's port number and store result
            //userInput = new Scanner(System.in);
            //portNumber = userInput.nextInt();
            Socket activePort = findActivePort( userIP );
            if (activePort == null)
            {
                portNumber = 1024;
                newNode = new Node( userName, userIP, portNumber );
                newNode.addNodeData(newNode.getCurrentNode());
                System.out.println( newNode.nodeInfoToString() );
                printConfirmation(newNode);
                newNode.startReceiver();
                newNode.startSender();
            }
            else
            {
                portNumber = getInactivePort( userIP );
                newNode = new Node( userName, userIP, portNumber );
                printConfirmation(newNode);
                newNode.startReceiver();
                getUpdatedInfo(activePort,newNode);
                newNode.startSender();
            }
            //System.out.println("Goodbye " + newNode.getCurrentNode()[0]);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.exit(1);
    }
    public static int getInactivePort(InetAddress userIP)
    {
        int portNum = 1024;
        while (true)
        {
            try
            {
                Socket testSocket = new Socket(userIP.getHostAddress(),portNum);
                testSocket.close();
                portNum++;
            }
            catch (Exception ex)
            {
                return portNum;
            }
        }
    }
    public static Socket findActivePort(InetAddress userIP)
    {
        for (int i = 1024; i < 1050; i++)
        {
            try
            {
                Socket testSocket = new Socket(userIP.getHostAddress(),i);
                return testSocket;
            }
            catch (Exception ex)
            {
                i++;
            }
        }
        return null;
    }
    public static void getUpdatedInfo( Socket inSocket, Node newNode )
    {
        try
        {
            ObjectOutputStream toNode = new ObjectOutputStream( inSocket.getOutputStream() );

            // sends back updated NodeInfo to the new Node trying to connect.
            Message joinMessage = new Message( newNode.getNodeInfo(), newNode.getCurrentNode(), 100, "" );
            toNode.writeObject( joinMessage );
            //System.out.println( "joinMessage sent successfully  " );

            //ObjectInputStream fromNode = new ObjectInputStream( inSocket.getInputStream() );
            //System.out.println( "Instream created clientSide" );

            //Message currentMessage = (Message) fromNode.readObject();
            //newNode.updateMesh( currentMessage.getNodeInfo() );
            //System.out.println( "join confirmation received" );
            //System.out.println( newNode.nodeInfoToString() );


            //sendConfirmation(newNode.getNodeInfo(), newNode.getCurrentNode());
            //return currentMessage.getNodeInfo();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
    public static void sendConfirmation(NodeInfo inNodeInfo, String[] currentNode)
    {
        try
        {
            for (int i = 0; i < inNodeInfo.getSize(); i++) {
                if (!inNodeInfo.get(i)[2].equals(currentNode[2]))
                {
                    //System.out.println("Trying to send confirmation to port: " + inNodeInfo.get(i)[1] + " " + inNodeInfo.get(i)[2]);
                    Socket sendSocket = new Socket(inNodeInfo.get(i)[1], Integer.parseInt(inNodeInfo.get(i)[2]));
                    Message joinedMessage = new Message( inNodeInfo, currentNode, 110, "" );
                    ObjectOutputStream toMesh = new ObjectOutputStream( sendSocket.getOutputStream() );
                    toMesh.writeObject( joinedMessage );
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    public static void printConfirmation(Node inNode)
    {
        System.out.println("Your node has been created!");
        System.out.println("Your port is " + inNode.getPortNumber());
        System.out.println("\nHere is the format for each type of message (entered without quotes):" +
                "\n    Join: 'join,<ipaddressofparticipantnode>'\n    Leave: 'leave'\n    " +
                "Note: 'note,<yournote>'");
        System.out.println("\nYou can start typing your messages...\n");
    }

}
