package vsp.api_client;


import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import vsp.Application;
import vsp.api_client.entities.QuestWrapper;
import vsp.api_client.entities.Token;
import vsp.api_client.entities.User;
import vsp.api_client.http.HTTPBasicAuth;
import vsp.api_client.http.HTTPRequest;
import vsp.api_client.http.HTTPVerb;
import vsp.api_client.utility.WebResource;

import java.io.IOException;

/**
 * Offers operations from the given API.
 */
public class APIClient {

    private static Logger LOG = Logger.getLogger(Application.class);

    @NotNull
    private static final String PROTOCOL = "http";

    @NotNull
    private String targetURL;

    public APIClient(@NotNull final String restApiAddress,
                     @NotNull final Integer restApiPort) {
        this.targetURL = String.format("%s://%s:%d", PROTOCOL, restApiAddress, restApiPort);
        LOG.debug("URL: " + targetURL);
    }

    /**
     * Registers a user to the blackboard.
     *
     * @param user Not null.
     * @return TODO
     * @throws IOException If connection fails.
     */
    public String register(@NotNull final User user) throws IOException {
        LOG.debug("Registration with user " + user.getName());
        return HTTPRequest
                .to(targetURL)
                .resource(WebResource.USERS)
                .type(HTTPVerb.POST)
                .body(user)
                .send()
                .getJson();
    }

    /**
     * Login to receive a authentication token.
     *
     * @param user Not null.
     * @return TODO
     * @throws IOException If connection fails.
     */
    public Token login(@NotNull final User user) throws IOException {
        LOG.debug("Login with user '" + user.getName() + "'...");
        return HTTPRequest
                .to(targetURL)
                .resource(WebResource.LOGIN)
                .type(HTTPVerb.GET)
                .auth(HTTPBasicAuth.forUser(user))
                .send()
                .getAs(Token.class);
    }

    /**
     * Checks given user.
     *
     * @param user Not null.
     * @return TODO
     * @throws IOException If connection fails.
     */
    public String whoAmI(@NotNull final User user) throws IOException {
        LOG.debug("WhoAmI with user " + user.getName());
        return HTTPRequest
                .to(targetURL)
                .resource(WebResource.WHOAMI)
                .type(HTTPVerb.GET)
                .auth(HTTPBasicAuth.forUser(user))
                .send()
                .getJson();
    }

    // TODO quests
    public QuestWrapper quests() throws IOException {
        LOG.debug("View quests");
        return HTTPRequest
                .to(targetURL)
                .resource(WebResource.QUESTS)
                .type(HTTPVerb.GET)
                .send()
                .getAs(QuestWrapper.class);
    }

    // TODO map
    public String map(@NotNull final String location) throws IOException {
        LOG.debug("View quests");
        return HTTPRequest
                .to(targetURL)
                //.resource(WebResource.MAP + location) // TODO
                .type(HTTPVerb.GET)
                .send()
                .getJson();
    }


}
