package org.it.me.colin.constants;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class TicTacToeServiceConstants {
    public static final String DEFAULT_SCHEME = "http";
    public static final String SERVICE_HOST = "127.0.0.1";
    public static final String SERVICE_PORT = "5000";

    public static final String AUTH_PATH = "key";
    public static final String ECHO_AUTH_PATH = "echo/auth"; // TODO: Path is actually "authenticate" not "auth"
    public static final String GAME_PATH = "game";
    public static final String MOVE_PATH = "move";

    public static final String X_PARAM = "x";
    public static final String Y_PARAM = "y";
    public static final String TILE_PARAM = "tile";

    public static UriComponentsBuilder baseUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme(DEFAULT_SCHEME)
                .host(SERVICE_HOST)
                .port(SERVICE_PORT);
    }
    public static final URI GET_AUTHENTICATION_KEY_URI = baseUriBuilder()
            .path(AUTH_PATH)
            .build().toUri();
    public static final URI VALIDATE_AUTHENTICATION_KEY_URI = baseUriBuilder()
            .path(ECHO_AUTH_PATH)
            .build().toUri();
    public static final URI CREATE_GAME_URI = baseUriBuilder()
            .path(GAME_PATH)
            .build().toUri();

    public static final String API_KEY_HEADER = "api-key";

    public static final String AUTH_SUCCESS_RESPONSE = "Auth OK"; // TODO: Response is actually "Auth OK" not "OK Auth"
    public static final String INVALID_MOVE_MESSAGE = "The browser (or proxy) sent a request that this server could not understand.";
    public static final String GAME_NOT_FOUND_MESSAGE = "The requested URL was not found on the server. If you entered the URL manually please check your spelling and try again.";
}
