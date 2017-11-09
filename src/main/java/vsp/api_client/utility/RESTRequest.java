package vsp.api_client.utility;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Representation of an request for a REST-API.
 */
public class RESTRequest {

    private static final Logger LOG = Logger.getLogger(RESTRequest.class);

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
     * Response message from the request. Set after {@link #send()}.
     */
    @Nullable
    private String response;

    /**
     * Type of request. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     */
    @Nullable
    private HTTPVerb connectionType;

    /**
     * Resource from the rest api. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     */
    @Nullable
    private WebResource resource;

    /**
     * Message body. Can be set with {@link #body(Object)}.
     */
    @Nullable
    private String body;

    /**
     * Private constructor; Use {@link #to(String)}.
     *
     * @param targetURL Not null.
     */
    private RESTRequest(@NotNull String targetURL) {
        this.targetURL = targetURL;
    }

    // ------ PUBLIC ---------

    /**
     * @param targetURL Address to the rest api.
     * @return This instance for inline use.
     */
    public static RESTRequest to(@NotNull final String targetURL) {
        Preconditions.checkNotNull(targetURL, "targetURL must not be null.");

        return new RESTRequest(targetURL);
    }

    /**
     * Web resource to connect to. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     *
     * @param resource Not null.
     * @return This instance for inline use.
     */
    public RESTRequest resource(@NotNull final WebResource resource) {
        Preconditions.checkNotNull(resource, "resource must not be null.");

        this.resource = resource;
        LOG.debug("Resource: " + this.resource.getValue());
        return this;
    }

    /**
     * Type of http verb for the connection. <b>HAS TO BE SET BEFORE {@link #send()}</b>
     *
     * @param type Not null.
     * @return This instance for inline use.
     */
    public RESTRequest type(@NotNull final HTTPVerb type) {
        Preconditions.checkNotNull(type, "type must not be null.");

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
    public <T> RESTRequest body(@NotNull final T obj) throws IOException {
        Preconditions.checkNotNull(obj, "obj must not be null.");

        this.body = new Gson().toJson(obj);
        LOG.debug("Body " + this.body);
        return this;
    }

    /**
     * Starts the get request to the given resource. <b>{@link #resource(WebResource)} and {@link #type(HTTPVerb)} has to be set before this method</b>
     *
     * @return This instance for inline use.
     * @throws IOException If connection fails.
     */
    @NotNull
    public RESTRequest send() throws IOException {
        Preconditions.checkState(connectionType != null, "connectionType has to be set");
        Preconditions.checkState(resource != null, "resource has to be set");

        final URL url = new URL(targetURL + resource.getValue());
        HttpURLConnection connection = establishConnection(url);
        response = extractResponse(connection);
        connection.disconnect();
        return this;
    }

    /**
     * Returns response message.
     *
     * @return May be null.
     */
    @Nullable
    public String getResponse() {
        return response;
    }

    // -------- PRIVATE --------

    /**
     * Return response message.
     *
     * @throws IOException If connection fails.
     */
    @NotNull
    private String extractResponse(@NotNull final HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = getReader(connection);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Returns reader for connection response.
     *
     * @return Not null.
     * @throws IOException If connection fails.
     */
    @NotNull
    private BufferedReader getReader(@NotNull final HttpURLConnection connection) throws IOException {
        return new BufferedReader(new InputStreamReader((connection.getInputStream())));
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
        Preconditions.checkState(resource != null, "resource has to be set");

        LOG.debug("Connecting to " + url.getHost() + url.getPort());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(connectionType.getValue());
        connection.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        // LOG.debug("Connection:" + connection.getRequestMethod() + connection.getHeaderFields().toString());

        if (body != null) {
            // send body (json)
            LOG.debug("Sending body...");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes(CHARSET));
            outputStream.close();
        }

        if (connection.getResponseCode() >= 300) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }

        return connection;
    }
}
