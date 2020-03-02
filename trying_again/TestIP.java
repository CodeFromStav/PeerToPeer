import java.net.*;
public class TestIP {
    public static void main(String[] args) {
        isReachable("127.0.0.3");
    }
    public static void isReachable(String address) {
            try {
                InetAddress inetAddress = InetAddress.getByName(address);
                boolean isReachable = inetAddress.isReachable(5000);
                System.out.printf("Is the address [%s] reachable? -%s\n", address, isReachable ? "Yes" : "No");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
