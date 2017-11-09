package vsp.api_client;


import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import vsp.Application;
import vsp.api_client.entities.User;
import vsp.api_client.utility.HTTPVerb;
import vsp.api_client.utility.RESTRequest;
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
     * Registers a user to the TODO
     *
     * @param user Not null.
     */
    public String register(@NotNull final User user) throws IOException {
        LOG.debug("Registration with user " + user.getName());
        return RESTRequest.to(targetURL)
                .resource(WebResource.USERS)
                .type(HTTPVerb.POST)
                .body(user)
                .send()
                .getResponse();
    }

}
