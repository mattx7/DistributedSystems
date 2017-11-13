package vsp.api_client;


import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import vsp.Application;
import vsp.api_client.entities.User;
import vsp.api_client.http.HTTPRequest;
import vsp.api_client.http.HTTPResponse;
import vsp.api_client.http.HTTPVerb;
import vsp.api_client.http.auth.HTTPBasicAuth;
import vsp.api_client.http.auth.HTTPTokenAuth;
import vsp.api_client.http.web_resource.DebugResource;
import vsp.api_client.http.web_resource.MainResource;
import vsp.api_client.http.web_resource.SubResource;

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

    // ======= for debug/testing ======

    public HTTPResponse get(@NotNull final User user,
                            @NotNull final String path) throws IOException {
        LOG.debug("Registration with user " + user.getName());
        return HTTPRequest
                .to(targetURL)
                .resource(new DebugResource(path))
                .type(HTTPVerb.GET)
                .body(user)
                .send();
    }

    public HTTPResponse post(@NotNull final User user,
                             @NotNull final String path,
                             @NotNull final String body) throws IOException {
        LOG.debug("Registration with user " + user.getName());
        return HTTPRequest
                .to(targetURL)
                .resource(new DebugResource(path))
                .type(HTTPVerb.POST)
                .body(user)
                .send();
    }

    //  =====================================

    /**
     * Registers a user to the blackboard.
     *
     * @param user Not null.
     * @return TODO
     * @throws IOException If connection fails.
     */
    public HTTPResponse register(@NotNull final User user) throws IOException {
        LOG.debug("Registration with user " + user.getName());
        return HTTPRequest
                .to(targetURL)
                .resource(MainResource.USERS)
                .type(HTTPVerb.POST)
                .body(user)
                .send();
    }

    /**
     * Login to receive a authentication token.
     *
     * @param user Not null.
     * @return TODO
     * @throws IOException If connection fails.
     */
    public HTTPResponse login(@NotNull final User user) throws IOException {
        LOG.debug("Login with user '" + user.getName() + "'...");
        return HTTPRequest
                .to(targetURL)
                .resource(MainResource.LOGIN)
                .type(HTTPVerb.GET)
                .auth(HTTPBasicAuth.forUser(user))
                .send();
    }

    /**
     * Checks given user.
     *
     * @param user Not null.
     * @return TODO
     * @throws IOException If connection fails.
     */
    public HTTPResponse whoAmI(@NotNull final User user) throws IOException {
        LOG.debug("WhoAmI with user " + user.getName());
        return HTTPRequest
                .to(targetURL)
                .resource(MainResource.WHOAMI)
                .type(HTTPVerb.GET)
                .auth(HTTPTokenAuth.forUser(user))
                .send();
    }

    // TODO quests
    public HTTPResponse quests() throws IOException {
        LOG.debug("View quests");
        return HTTPRequest
                .to(targetURL)
                .resource(MainResource.QUESTS)
                .type(HTTPVerb.GET)
                .send();
    }

    public HTTPResponse questDeliveries(@NotNull final User user,
                                        @NotNull final Integer questId) throws IOException {
        LOG.debug("View quests");
        return HTTPRequest
                .to(targetURL)
                .resource(SubResource.from(
                        MainResource.QUESTS,
                        String.valueOf(questId),
                        "deliveries"))
                .type(HTTPVerb.GET)
                .auth(HTTPBasicAuth.forUser(user))
                .send();
    }

    public HTTPResponse questTasks(@NotNull final User user,
                                   @NotNull final Integer questId) throws IOException {
        LOG.debug("View quests");
        return HTTPRequest
                .to(targetURL)
                .resource(SubResource.from(MainResource.QUESTS, String.valueOf(questId), "tasks"))
                .type(HTTPVerb.GET)
                .auth(HTTPBasicAuth.forUser(user))
                .send();
    }

    // TODO map
    public HTTPResponse map(@NotNull final User user,
                            @NotNull final String location) throws IOException {
        LOG.debug("View quests");
        return HTTPRequest
                .to(targetURL)
                .resource(SubResource.from(MainResource.MAP, location))
                .type(HTTPVerb.GET)
                .auth(HTTPBasicAuth.forUser(user))
                .send();
    }


}
