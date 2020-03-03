import java.io.*;
import java.net.*;
import java.util.*;

public class StartNode
{
    public static void main(String[] args)
    {

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
            // Letting user know of IP Address retrieval

            // Get the user's Hostname/IP address
            userAddress = InetAddress.getLocalHost();

            // Split userAddress for IP Address
            userAddressSplit = userAddress.toString().split("/");

            // Converting String object of IP address to InetAddress object
            userIP = InetAddress.getByName(userAddressSplit[1]);


            System.out.print("Please enter your userName: ");

            // Scan in user's userName and store result
            userInput = new Scanner(System.in);
            userName = userInput.nextLine();

            // Ask user for their port number from terminal/cmd
            Socket activePort = findActivePort(userIP);
            if (activePort == null)
            {
                portNumber = 1024;
                newNode = new Node(userName, userIP, portNumber);
                newNode.addNodeData(newNode.getCurrentNode());
                //System.out.println(newNode.nodeInfoToString());
                printConfirmation(newNode);
                newNode.startReceiver();
                newNode.startSender();
            }
            else
            {
                portNumber = getInactivePort(userIP);
                newNode = new Node(userName, userIP, portNumber);
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
    //allows us to check if node not active
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
    //port between 1024 and 1050
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
    //updates nodeInfo for this class
    public static void getUpdatedInfo(Socket inSocket, Node newNode)
    {
        try
        {
            ObjectOutputStream toNode = new ObjectOutputStream(inSocket.getOutputStream());

            // sends back updated NodeInfo to the new Node trying to connect.
            Message joinMessage = new Message(newNode.getNodeInfo(), newNode.getCurrentNode(), 100, "");
            //System.out.println("Node: " + newNode.getCurrentNode()[0] + " has joined.");
            toNode.writeObject(joinMessage);
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //Confirms Join
    public static void sendConfirmation(NodeInfo inNodeInfo, String[] currentNode)
    {
        try
        {
            for (int i = 0; i < inNodeInfo.getSize(); i++) {
                if (!inNodeInfo.get(i)[2].equals(currentNode[2]))
                {
                    //System.out.println("Trying to send confirmation to port: " + inNodeInfo.get(i)[1] + " " + inNodeInfo.get(i)[2]);
                    Socket sendSocket = new Socket(inNodeInfo.get(i)[1], Integer.parseInt(inNodeInfo.get(i)[2]));
                    Message joinedMessage = new Message(inNodeInfo, currentNode, 110, "");
                    ObjectOutputStream toMesh = new ObjectOutputStream(sendSocket.getOutputStream());
                    System.out.println("Node: " + currentNode[0] + " has joined.");
                    toMesh.writeObject(joinedMessage);
                }
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //print function
    public static void printConfirmation(Node inNode)
    {
        System.out.println(inNode.getCurrentNode()[0] + " has entered the chat.");
        System.out.println("\nYou can start typing your messages...\n");
    }

}
