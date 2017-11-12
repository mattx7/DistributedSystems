package vsp.api_client.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Token {

    @NotNull
    private final String token;

    @NotNull
    private final Date date; // TODO test if this is set after json conversion


    public Token(@NotNull final String token,
                 @NotNull final Date date) {
        this.token = token;
        this.date = date;
    }

    @NotNull
    public String getToken() {
        return token;
    }

    @NotNull
    public Date getDate() {
        return date;
    }
}
