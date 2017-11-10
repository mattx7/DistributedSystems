package vsp;

import org.apache.log4j.Logger;
import vsp.api_client.APIClient;
import vsp.api_client.entities.User;
import vsp.api_client.utility.BlackBoard;

import java.io.IOException;
import java.net.InetAddress;

public class Application {

    private static final int BLACKBOARD_PORT = 24000;
    private static Logger LOG = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        InetAddress apiAddress;

        try {
            apiAddress = BlackBoard.getIP(BLACKBOARD_PORT); // TODO Port

            LOG.debug("apiAddress: " + apiAddress.getHostName() + " Hostaddress:" + apiAddress.getHostAddress());


            // COMMANDLINE INTERFACE
            System.out.println("Please register with your username & password!");
            //Console console = System.console();
            //String username = console.readLine("Username:");
            //String password = console.readLine("password:");

            APIClient client = new APIClient(apiAddress.getHostAddress(), 5000);

            System.out.println(client.login(new User("Peter Griffin", "1234")));

        } catch (final IOException e) {
            LOG.error(e);
        }
    }
}
