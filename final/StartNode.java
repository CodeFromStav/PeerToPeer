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

        try
        {
            // Letting user know of IP Address retrieval
            System.out.println("Retrieving IP Address...");

            // Get the user's Hostname/IP address
            userAddress = InetAddress.getLocalHost();

            // Split userAddress for IP Address
            userAddressSplit = userAddress.toString().split("/");

            // Converting String object of IP address to InetAddress object
            userIP = InetAddress.getByName(userAddressSplit[1]);

            // Printing out user's IP Address to the screen
            System.out.println("IP Address found! Your IP Address: " + userAddressSplit[1]);

            // Ask user for their userName from terminal/cmd
            System.out.print("Please enter your userName: ");

            // Scan in user's userName and store result
            userInput = new Scanner( System.in );
            userName = userInput.nextLine();

            // Ask user for their port number from terminal/cmd
            System.out.print("Please enter your port number: ");

            // Scan in user's port number and store result
            userInput = new Scanner(System.in);
            portNumber = userInput.nextInt();

            // Ask user for if they're the first user in this chat session
            System.out.print("Are you the first participant? (yes/no) ");

            userInput = new Scanner(System.in);
            firstParticipant = userInput.nextLine();

            // Create new Node object with data supplied by user
            Node newNode = new Node( userName, userIP, portNumber );

            // Node creation confirmation
            System.out.println("Your node has been created!");

            // Layout for 3 types of message: Join, Leave, Note
            //      Join message is required for new nodes
            System.out.println("\nHere is the format for each type of message (entered without quotes):" +
                    "\n    Join: 'join,<ipaddressofparticipantnode>'\n    Leave: 'leave'\n    " +
                    "Note: 'note,<yournote>'");

            // Create a new Receiver Thread object using user's IP, socket and port number

            // Warning to new nodes to join first
            if (firstParticipant.equalsIgnoreCase("no"))
            {
                System.out.println("\nNew users need to 'join' first!");
            }

            // Spawn a thread for reading messages
            //receiverThread.start();

            // Create a new Sender Thread object using user's IP, userName, Socket and port#
            // Thread senderThread = new Thread(new Sender(userIP, userName, nodeSocket, portNumber));

            // Spawn a thread for sending messages


            // Let the user know that they can start sending messages
            System.out.println("\nYou can start typing your messages...\n");
        }

        catch (IOException e)
        {
            System.out.println("Failed to create Datagram Socket!");
            e.printStackTrace();
        }

    }
}
