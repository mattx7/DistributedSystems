package vsp.api_client.http;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vsp.api_client.http.auth.HTTPAuthentication;
import vsp.api_client.http.web_resource.WebResource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Representation of an request for a REST-API.
 */
public class HTTPRequest {

    private static final Logger LOG = Logger.getLogger(HTTPRequest.class);

    /**
     * Connection timeout in ms.
     */
    private static final int CONNECTION_TIMEOUT = 2000;

    /**
     * Charset for this connection.
     */
    private static final String CHARSET = "UTF-8";

    /**
     * Address of the rest api.
     */
    @NotNull
    private String targetURL;

    /**
     * Type of request. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     */
    @Nullable
    private HTTPVerb connectionType;

    /**
     * Resource from the rest api. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     */
    @Nullable
    private WebResource webResource;

    /**
     * Message body. Can be set with {@link #body(Object)}.
     */
    @Nullable
    private String body;

    /**
     * HTTP authentication. Can be set with {@link #auth(HTTPAuthentication)}}.
     */
    private HTTPAuthentication authentication;

    /**
     * Private constructor; Use {@link #to(String)}.
     *
     * @param targetURL Not null.
     */
    private HTTPRequest(@NotNull String targetURL) {
        this.targetURL = targetURL;
    }

    // ------ PUBLIC ---------

    /**
     * @param targetURL Address to the rest api.
     * @return This instance for inline use.
     */
    public static HTTPRequest to(@NotNull final String targetURL) {
        Preconditions.checkNotNull(targetURL, "targetURL should not be null.");

        return new HTTPRequest(targetURL);
    }

    /**
     * Web resource to connect to. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     *
     * @param resource Not null.
     * @return This instance for inline use.
     */
    public HTTPRequest resource(@NotNull final WebResource resource) {
        Preconditions.checkNotNull(resource, "webResource should not be null.");

        this.webResource = resource;
        LOG.debug("Resource: " + this.webResource.getPath());
        return this;
    }

    /**
     * Type of http verb for the connection. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     *
     * @param type Not null.
     * @return This instance for inline use.
     */
    public HTTPRequest type(@NotNull final HTTPVerb type) {
        Preconditions.checkNotNull(type, "type should not be null.");

        this.connectionType = type;
        LOG.debug("HTTP Verb: " + this.connectionType.getValue());
        return this;
    }

    /**
     * Converts an object to JSON and saves it.
     *
     * @param obj Not null.
     * @return This instance for inline use.
     */
    public <T> HTTPRequest body(@NotNull final T obj) throws IOException {
        Preconditions.checkNotNull(obj, "obj should not be null.");

        this.body = new Gson().toJson(obj);
        LOG.debug("Body " + this.body);
        return this;
    }

    /**
     * Sets a authentication for the http connection.
     *
     * @param authentication Not null.
     * @return This instance for inline use.
     */
    public HTTPRequest auth(@NotNull final HTTPAuthentication authentication) {
        Preconditions.checkNotNull(authentication, "authentication should not be null.");

        this.authentication = authentication;
        LOG.debug("Auth: " + authentication.getDebugInfo());
        return this;
    }

    /**
     * Starts the get request to the given resource. <b>{@link #resource(WebResource)} and {@link #type(HTTPVerb)} has to be set before this method</b>
     *
     * @return This instance for inline use.
     * @throws IOException If connection fails.
     */
    @NotNull
    public HTTPResponse send() throws IOException {
        Preconditions.checkState(connectionType != null, "connectionType has to be set");
        Preconditions.checkState(webResource != null, "webResource has to be set");

        final URL url = new URL(targetURL + webResource.getPath());
        HttpURLConnection connection = establishConnection(url);
        HTTPResponse response = new HTTPResponse(extractResponse(connection));
        connection.disconnect();
        return response;
    }

    // -------- PRIVATE --------

    /**
     * Returns buffered reader for input stream response.
     *
     * @return Not null.
     */
    @NotNull
    private BufferedReader getReader(@NotNull final InputStream inputStream) {
        return new BufferedReader(new InputStreamReader((inputStream)));
    }


    /**
     * Return response message.
     *
     * @throws IOException If connection fails.
     */
    @NotNull //TODO really NotNull?
    private String extractResponse(@NotNull final HttpURLConnection connection) throws IOException {
        final boolean noErrorOccurred = connection.getErrorStream() == null;
        BufferedReader responseReader = noErrorOccurred ?
                getReader(connection.getInputStream()) :
                getReader(connection.getErrorStream());

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = responseReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        if (noErrorOccurred)
            return stringBuilder.toString();
        else
            throw new HTTPConnectionException(
                    connection.getResponseCode(),
                    connection.getResponseMessage(),
                    stringBuilder.toString());
    }

    /**
     * Starts the connection. <b>{@link #resource(WebResource)} and {@link #type(HTTPVerb)} has to be set before this method</b>
     *
     * @param url address to connect to.
     * @return Not null.
     * @throws IOException If connection fails.
     */
    private HttpURLConnection establishConnection(@NotNull final URL url) throws IOException {
        Preconditions.checkState(connectionType != null, "connectionType has to be set");
        Preconditions.checkState(webResource != null, "webResource has to be set");

        LOG.debug("Connecting to " + url.getHost() + ":" + url.getPort() + url.getPath());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(connectionType.getValue());
        connection.setConnectTimeout(CONNECTION_TIMEOUT);

        connection.addRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
        if (authentication != null) {
            LOG.debug("settings auth header...");
            connection.addRequestProperty("Authorization", authentication.getAuthHeader());
        }

        if (body != null) {
            // send body (json)
            LOG.debug("Sending body...");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes(CHARSET));
            outputStream.close();
        }
        LOG.debug("Connection:" + connection.getRequestMethod() + connection.getHeaderFields().toString());
        return connection;
    }
}
