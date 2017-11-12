package vsp;

import org.apache.log4j.Logger;
import vsp.api_client.APIClient;
import vsp.api_client.entities.Quest;
import vsp.api_client.entities.Token;
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

            // COMMANDLINE INTERFACE
            //System.out.println("Please register with your username & password!");
            // Console console = System.console();
            //String username = console.readLine("Username:");
            //String password = console.readLine("password:");

            APIClient client = new APIClient(apiAddress.getHostAddress(), 5000);
            User alreadyExistingTestUser = new User("Peter Griffin", "1234");

            // System.out.println(client.register(alreadyExistingTestUser));
            Token token = client.login(alreadyExistingTestUser);
            LOG.debug("Token received: " + token.getToken());

            System.out.println(client.whoAmI(alreadyExistingTestUser)); // TODO maybe stay with this default json output!?

            System.out.println("Quests:");
            client.quests().getObjects()
                    .stream()
                    .map(Quest::getName)
                    .forEach(System.out::println);


        } catch (final IOException e) {
            LOG.error(e);
        }
    }
}
