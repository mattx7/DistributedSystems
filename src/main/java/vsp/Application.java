package vsp;

import org.apache.log4j.Logger;
import vsp.utility.BlackBoard;

import java.io.Console;
import java.io.IOException;
import java.net.InetAddress;

public class Application {

    private static final int BLACKBOARD_PORT = 24000;
    private static Logger LOG = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        InetAddress restAddress = null;
        try {
            restAddress = BlackBoard.remoteIpAddress(BLACKBOARD_PORT);
            LOG.debug("restAddress: " + restAddress.getHostName() + " Hostaddress:" + restAddress.getHostAddress());
        } catch (IOException ex) {
            System.err.println("main(): " + ex);
        }

        // COMANDLINE INTERFACE
        System.out.println("Please register with your username & password!");
        Console console = System.console();
        String username = console.readLine("Username:");
        String password = console.readLine("password:");
    }
}
