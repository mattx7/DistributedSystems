package vsp.api_client.entities;

import org.jetbrains.annotations.NotNull;

/**
 * Representation of a user from the rest api.
 */
public class User {

    @NotNull
    private String name;

    @NotNull
    private String password;

    public User(@NotNull String name, @NotNull String password) {
        this.name = name;
        this.password = password;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPassword() {
        return password;
    }
}
