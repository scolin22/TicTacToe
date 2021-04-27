package org.it.me.colin.client;

import com.google.gson.Gson;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.it.me.colin.exception.ClientException;
import org.it.me.colin.exception.GameNotFoundException;
import org.it.me.colin.exception.InvalidPlayerMoveException;
import org.it.me.colin.model.GameResponse;
import org.it.me.colin.model.Tile;
import org.it.me.colin.util.HttpUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.it.me.colin.constants.TicTacToeServiceConstants.*;

/**
 * This class implements {@link TicTacToeClient} using an {@link HttpClient} to send requests to a locally hosted
 * TicTacToe service.
 *
 * This class handles lazy loading an authentication key and uses it for all requests.
 *
 * TODO: Add retry handler
 * TODO: Abstract away client configuration and use a factory to build clients
 */
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TicTacToeHttpClient implements TicTacToeClient {
    private static final Gson GSON = new Gson();

    private final HttpClient httpClient;

    public String getAuthenticationKey() {
        log.debug("Initializing authentication key.");
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(DEFAULT_SCHEME)
                .host(SERVICE_HOST)
                .port(SERVICE_PORT)
                .path(AUTH_PATH)
                .build().toUri();

        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .build();

        HttpResponse<String> response = HttpUtils.handleHttpResponse(request, httpClient);
        return response.body();
    }

    public boolean validateAuthenticationKey(String authKey) {
        log.debug(String.format("Validating authentication key: %s", authKey));
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(DEFAULT_SCHEME)
                .host(SERVICE_HOST)
                .port(SERVICE_PORT)
                .path(ECHO_AUTH_PATH)
                .build().toUri();

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header(API_KEY_HEADER, authKey)
                .GET()
                .build();

        HttpResponse<String> response = HttpUtils.handleHttpResponse(request, httpClient);

        return AUTH_SUCCESS_RESPONSE.equals(response.body());
    }

    public GameResponse createGame(String authKey) {
        log.debug("Creating game.");
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(DEFAULT_SCHEME)
                .host(SERVICE_HOST)
                .port(SERVICE_PORT)
                .path(GAME_PATH)
                .build().toUri();

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header(API_KEY_HEADER, authKey)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = HttpUtils.handleHttpResponse(request, httpClient);

        // TODO: Implement a customer BodyHandlers that can handle JSON to GameResponse marshalling. But not all responses are JSON.
        return GSON.fromJson(response.body(), GameResponse.class);
    }

    public GameResponse getGame(String authKey, String gameId) {
        log.debug(String.format("Getting game with gameId: %s", gameId));
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(DEFAULT_SCHEME)
                .host(SERVICE_HOST)
                .port(SERVICE_PORT)
                .pathSegment(GAME_PATH, gameId)
                .build().toUri();

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header(API_KEY_HEADER, authKey)
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpUtils.handleHttpResponse(request, httpClient);
            return GSON.fromJson(response.body(), GameResponse.class);
        } catch (ClientException e) {
            if (e.getMessage().contains(GAME_NOT_FOUND_MESSAGE)) {
                throw new GameNotFoundException("Game was not found.");
            }
            throw e;
        }
    }

    public GameResponse makeMove(String authKey, String gameId, Tile tile, int x, int y) throws InvalidPlayerMoveException {
        log.debug(String.format("Making move for game with gameId: %s tile: %s x: %s y: %s", gameId, tile, x, y));
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(DEFAULT_SCHEME)
                .host(SERVICE_HOST)
                .port(SERVICE_PORT)
                .pathSegment(GAME_PATH, gameId, MOVE_PATH)
                .queryParam(X_PARAM, String.valueOf(x))
                .queryParam(Y_PARAM, String.valueOf(y))
                .queryParam(TILE_PARAM, tile.name())
                .build().toUri();

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header(API_KEY_HEADER, authKey)
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();

        try {
            HttpResponse<String> response = HttpUtils.handleHttpResponse(request, httpClient);
            return GSON.fromJson(response.body(), GameResponse.class);
        } catch (ClientException e) {
            if (e.getMessage().contains(INVALID_MOVE_MESSAGE)) {
                throw new InvalidPlayerMoveException("The player's move was invalid.");
            }
            throw e;
        }
    }
}
