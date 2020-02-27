import java.net.*;
import java.io.*;

/* Test class to verify server-client connection functionality */
public class Client {
    public static void main(String[] args) throws Exception {
        try {
            // open up connection on right port
            Socket clientSock = new Socket("127.0.0.2", 8080);
            /*// the variable holding a reference to the input stream pouring in characters from the client
            DataInputStream fromClient = new DataInputStream(clientSock.getInputStream());
            // the variable holding a reference to the output stream that is sending characters back to the client
            DataOutputStream toClient = new DataOutputStream(clientSock.getOutputStream());
            // create new bufferreader object to hold data from input stream
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage = "";

            // examples of shutdown sequences include: 'quit', 'q123u$#i66t', 'q0u0i0t', 'q u i t'
            while (!checkState(clientMessage)) {
                // control message for notifying user to enter text
                System.out.println("Enter text :");
                // reading in client's message
                clientMessage = buffRead.readLine();
                // constructing client's message
                toClient.writeUTF(clientMessage);
                // print client's message out
                toClient.flush();
                serverMessage = fromClient.readUTF();
                System.out.println(serverMessage);
            }
            // control message for when the user enters the word 'quit'
            System.out.println("Client has entered the 'quit' keyword; the connection has been closed.");
            //close I/O stream, bufferReader, and clientSocket
            toClient.close();
            buffRead.close();
            clientSock.close();

        */} catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /* Checks the state of the client's message, if quit is found the connection is terminated */
    private static boolean checkState(String state) {
        // removes all non-english characters (such as # and 3) in the string 'state' to make it easier to look for
        //      'quit'
        state = state.replaceAll("[^a-zA-Z ]", "");
        // removes all spaces in the string 'state' to make it easier to look for 'quit'
        state = state.replaceAll("\\s+", "");
        return state.contains("quit");
    }
}
