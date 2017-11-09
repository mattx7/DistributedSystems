package vsp.api_client.utility;

import org.jetbrains.annotations.NotNull;

/**
 * Offers all possible resources from the rest api.
 */
public enum WebResource {
    USERS("/users");

    @NotNull
    private String value;

    WebResource(@NotNull final String value) {
        this.value = value;
    }

    @NotNull
    public String getValue() {
        return value;
    }
}
