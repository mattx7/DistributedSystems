package vsp.api_client.http;

import java.io.IOException;

/**
 *
 */
public class HTTPConnectionException extends IOException {

    private int errorCode;

    HTTPConnectionException(int errorCode) {
        super("Connection Failed : HTTP error code : " + errorCode);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
