package vsp.api_client.http;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 *
 */
public class HTTPConnectionException extends IOException {

    private final int errorCode;

    HTTPConnectionException(final int errorCode,
                            @NotNull final String message) {
        super(errorCode + " " + message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
