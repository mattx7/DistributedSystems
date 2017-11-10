package vsp.api_client.utility;

import org.jetbrains.annotations.NotNull;

/**
 * Offers all possible resources from the rest api.
 */
public enum WebResource {
    USERS("/users"), LOGIN("/login"), WHOAMI("/whoami"), QUESTS("/blackboard/quests"), MAP("/map");

    @NotNull
    private String path;

    WebResource(@NotNull final String path) {
        this.path = path;
    }

    @NotNull
    public String getPath() {
        return path;
    }
}
