import java.net.*;
import java.io.*;

/* Test class to verify server-client connection functionality */
public class Client {
    public static void main(String[] args) throws Exception {
        IPAddress ip = new IPAddress( "127.0.0.1" );
        findUsableIP(ip);
    }

    private static Socket findUsableIP(IPAddress ip)
    {
        String ipString = ip.toString();
        if ( ipString == "127.255.255.250" )
        {
            System.out.println( "server is down" );
            return null;
        }
        try
        {
            Socket clientSock = new Socket( ipString, 8080 );
            return clientSock;
        }
        catch ( Exception ex ) {
            System.out.println( "IP " + ipString + " is not on network." );
            ip.incrementIP();
            return findUsableIP(ip);
        }
    }

}
