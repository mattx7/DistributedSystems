package vsp.utility;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Starts a connection, saves the result and closes the connection after that.
 */
public class GETRequest {

    /**
     * URL to connect to.
     */
    @NotNull
    private final URL url;

    /**
     * Response message from connection.
     */
    @NotNull
    private String response;

    /**
     * Constructor starts a connection via http.
     *
     * @param url address to connect.
     * @throws IOException If connection fails.
     */
    public GETRequest(@NotNull String url) throws IOException {
        this.url = new URL(url);
        HttpURLConnection connection = establishConnection();
        response = extractResponse(connection);
        connection.disconnect();
    }

    /**
     * Returns response message.
     *
     * @return Not null.
     */
    @NotNull
    public String getResponse() {
        return response;
    }

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
            stringBuilder.append(line);
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
     * Starts the connection.
     *
     * @return Not null.
     * @throws IOException If connection fails.
     */
    private HttpURLConnection establishConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }

        return connection;
    }

}
