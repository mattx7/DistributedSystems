package vsp;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import vsp.api_client.APIClient;
import vsp.api_client.entities.Token;
import vsp.api_client.entities.User;
import vsp.api_client.http.HTTPConnectionException;
import vsp.api_client.http.HTTPResponse;
import vsp.api_client.utility.BlackBoard;

import java.io.Console;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Runs application and interactions.
 */
public class Application {
    private static final String HELP = "!help";
    private static final String WHOAMI = "!whoami";
    private static final String QUESTS = "!quests";
    private static final String MAP = "!map";
    private static final String DELIVERIES = "!deliveries";
    private static final String TASK = "!task";
    private static final String QUIT = "!quit";
    private static final String GET = "!get";
    private static final String POST = "!post";
    private static final String AWAIT_COMMAND_MARKER = "#IN>";
    private static Logger LOG = Logger.getLogger(Application.class);

    /**
     * Given port for the blackboard.
     */
    private static final int BLACKBOARD_PORT = 24000;
    private static Console terminal;

    /**
     * Holds only the main method an instance is not necessary.
     */
    private Application() {
    }

    public static void main(String[] args) {
        InetAddress apiAddress;

        try {
            apiAddress = BlackBoard.getIP(BLACKBOARD_PORT); // TODO Port
            APIClient client = new APIClient(apiAddress.getHostAddress(), 5000);

            // interactions
            User user = insertUser();
            handleRegisterIfNecessary(client, user);
            showHelpMessage();
            awaitAndHandleCommand(client, user);

        } catch (final IOException e) {
            LOG.error(e);
        }
    }

    private static void awaitAndHandleCommand(@NotNull final APIClient client,
                                              @NotNull final User user) throws IOException {
        boolean holdAlive = true;
        while (holdAlive) {
            // input
            String[] parameter = terminal.readLine(AWAIT_COMMAND_MARKER).split(" ");

            try {
                if (parameter.length == 1) {
                    // commands with one param
                    switch (parameter[0]) {
                        case QUIT:
                            print("BYE!");
                            holdAlive = false;
                            break;
                        case WHOAMI:
                            print("whoAmI...");
                            print(client.whoAmI(user).getJson());
                            break;
                        case QUESTS:
                            print("Quests...");
                            print(client.quests().getJson());
                            /*  client
                                    .quests()
                                    .getAs(QuestWrapper.class) // TODO make null safe
                                    .getObjects()
                                    .stream()
                                    .map(e -> e.getId() + ": " + e.getName())
                                    .forEach(Application::print);*/
                            break;
                        default:
                            showHelpMessage();
                            break;
                    }
                } else if (parameter.length == 2) {
                    // commands with two params
                    String param1 = parameter[0];
                    String param2 = parameter[1];

                    switch (param1) {
                        case MAP:
                            print("Map...");
                            print(client.map(user, param2).getJson());
                            break;
                        case DELIVERIES:
                            print("Deliveries...");
                            print(client
                                    .questDeliveries(user, Integer.valueOf(param2))
                                    .getJson());
                        case TASK:
                            print("Task...");
                            print(client
                                    .questTasks(user, Integer.valueOf(param2))
                                    .getJson());
                        case GET:
                            print(client.get(user, param2).getJson());
                        default:
                            showHelpMessage();
                            break;
                    }
                } else if (parameter.length == 3) {
                    // commands with three params
                    String param1 = parameter[0];
                    String param2 = parameter[1];
                    String param3 = parameter[2];

                    switch (param1) {
                        case POST:
                            print(client.post(user, param2, param3).getJson());
                        default:
                            showHelpMessage();
                            break;
                    }

                } else {
                    showHelpMessage();
                }
            } catch (final IOException | NumberFormatException e) {
                LOG.error(e);
            }
        }
    }

    @NotNull
    private static User insertUser() {
        print("Please login or register with a username and password!");
        terminal = System.console();
        String username = terminal.readLine("Username: ");
        String password = terminal.readLine("password: ");
        return new User(username, password);
    }

    private static void showHelpMessage() {
        print("Possible commands: \n" +
                HELP + " - for this output \n" +
                QUIT + " - closes the terminal \n" +
                WHOAMI + " - information about me \n" +
                QUESTS + " - view open quests \n" +
                MAP + " <location> - view the given location \n" +
                DELIVERIES + " <questId> - ??? \n" +
                TASK + "\" <questId> - ??? \n" +
                "Debug commands: \n" +
                GET + " <path> - GET on given path \n" +
                POST + " <path> <body> - POST with given path and body \n"
        );
    }

    private static void handleRegisterIfNecessary(@NotNull final APIClient client,
                                                  @NotNull final User user) throws IOException {
        try {
            final HTTPResponse response = client.register(user);
            LOG.debug(response);
            print("User is now registered!");

        } catch (final HTTPConnectionException e) {
            if (e.getErrorCode() == 409)
                print("User already registered!");
            else
                LOG.error(e.getMessage());
        }

        // login
        final Token token = client.login(user).getAs(Token.class);
        user.setToken(token);
        print((token == null) ? "Login failed!" : "User logged in");
    }

    private static void print(@NotNull String message) {
        System.out.println(message);
    }
}
