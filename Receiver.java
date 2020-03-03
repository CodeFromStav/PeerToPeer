import java.net.*;

// Class to separate instances of server and client connections
public class Receiver implements Runnable {

    // Running thread associated with client
    Thread currentThread;
    NodeInfo nodeInfo;
    String[] currentNode;
    Node inNode;

    // Socket associated with client's connection
    public Receiver(NodeInfo nodeInfo, String[] currentNode, Node inNode) {
        this.nodeInfo = nodeInfo;
        this.currentNode = currentNode;
        this.inNode = inNode;
    }

    // Starting Receiver thread
    public void run() {
        try {
            ServerSocket server = new ServerSocket(Integer.parseInt(currentNode[2]));

            while (true) {
                Socket serverClient = server.accept();
                ReceiverHelper inMessage = new ReceiverHelper(serverClient, nodeInfo, currentNode, inNode);
                inMessage.start();
            }
        }
        catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    // Creates a new thread to start a server-client connection
    public void start() {
        if (currentThread == null) {
            currentThread = new Thread(this);
            currentThread.start();
        }
    }
}
