package org.it.me.colin.client;

import com.google.common.collect.ImmutableList;
import org.it.me.colin.exception.GameNotFoundException;
import org.it.me.colin.exception.InvalidPlayerMoveException;
import org.it.me.colin.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.it.me.colin.TestConstants.*;
import static org.it.me.colin.constants.TicTacToeServiceConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicTacToeHttpClientTest {

    @Mock
    private HttpClient mockHttpClient;

    private TicTacToeHttpClient client;

    @BeforeEach
    void setUp() {
        client = new TicTacToeHttpClient(mockHttpClient);
    }

    @Test
    void getAuthenticationKey_HappyCase_Success(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        HttpRequest request = HttpRequest.newBuilder(GET_AUTHENTICATION_KEY_URI).GET().build();
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(AUTH_KEY);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        String actualAuthKey = client.getAuthenticationKey();

        // Assert
        assertEquals(AUTH_KEY, actualAuthKey);
    }

    @Test
    void validateAuthenticationKey_HappyCase_Success(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        HttpRequest request = HttpRequest.newBuilder(VALIDATE_AUTHENTICATION_KEY_URI).header(API_KEY_HEADER, AUTH_KEY).GET().build();
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(AUTH_SUCCESS_RESPONSE);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        // Assert
        assertTrue(client.validateAuthenticationKey(AUTH_KEY));
    }

    @Test
    void createGame_HappyCase_Success(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        HttpRequest request = HttpRequest.newBuilder(CREATE_GAME_URI)
                .header(API_KEY_HEADER, AUTH_KEY).POST(HttpRequest.BodyPublishers.ofString("")).build();
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(CREATE_GAME_RESPONSE_JSON);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        GameResponse actualGameResponse = client.createGame(AUTH_KEY);

        // Assert
        assertEquals(GAME_RESPONSE, actualGameResponse);
    }

    @Test
    void getGame_HappyCase_Success(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        URI uri = baseUriBuilder().pathSegment(GAME_PATH, GAME_ID).build().toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).header(API_KEY_HEADER, AUTH_KEY).GET().build();
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(GET_GAME_RESPONSE_JSON);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        GameResponse actualGameResponse = client.getGame(AUTH_KEY, GAME_ID);

        // Assert
        assertEquals(GAME_RESPONSE, actualGameResponse);
    }

    @Test
    void getGame_NotFound_ThrowsGameNotFoundException(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        URI uri = baseUriBuilder().pathSegment(GAME_PATH, GAME_ID).build().toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).header(API_KEY_HEADER, AUTH_KEY).GET().build();
        when(mockHttpResponse.statusCode()).thenReturn(404);
        when(mockHttpResponse.body()).thenReturn(GAME_NOT_FOUND_RESPONSE_HTML);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        // Assert
        assertThrows(GameNotFoundException.class, () -> client.getGame(AUTH_KEY, GAME_ID));
    }

    @Test
    void makeMove_HappyCase_Success(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        URI uri = baseUriBuilder().pathSegment(GAME_PATH, GAME_ID, MOVE_PATH).queryParam(X_PARAM, 0).queryParam(Y_PARAM, 0).queryParam(TILE_PARAM, Tile.X).build().toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).header(API_KEY_HEADER, AUTH_KEY).PUT(HttpRequest.BodyPublishers.ofString("")).build();
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(MAKE_MOVE_RESPONSE_JSON);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        GameResponse actualGameResponse = client.makeMove(AUTH_KEY, GAME_ID, Tile.X, 0, 0);

        // Assert
        List<GameTile> gameBoardWithMove = ImmutableList.of(
                GameTile.builder().tile(Tile.X).x(0).y(0).build(),
                GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
                GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
                GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
                GameTile.builder().tile(Tile.EMPTY).x(1).y(1).build(),
                GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
                GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
                GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
                GameTile.builder().tile(Tile.EMPTY).x(2).y(2).build());
        GameResponse gameResponseWithMove = GameResponse.builder()
                .gameId(GAME_ID)
                .gameBoard(gameBoardWithMove)
                .status(Status.NONE)
                .winner(Winner.NONE)
                .build();
        assertEquals(gameResponseWithMove, actualGameResponse);
    }

    @Test
    void makeMove_InvalidMove_ThrowsInvalidPlayerMoveException(@Mock HttpResponse<String> mockHttpResponse) throws Exception {
        // Setup
        URI uri = baseUriBuilder().pathSegment(GAME_PATH, GAME_ID, MOVE_PATH).queryParam(X_PARAM, 0).queryParam(Y_PARAM, 0).queryParam(TILE_PARAM, Tile.X).build().toUri();
        HttpRequest request = HttpRequest.newBuilder(uri).header(API_KEY_HEADER, AUTH_KEY).PUT(HttpRequest.BodyPublishers.ofString("")).build();
        when(mockHttpResponse.statusCode()).thenReturn(400);
        when(mockHttpResponse.body()).thenReturn(INVALID_MOVE_RESPONSE_HTML);
        when(mockHttpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mockHttpResponse);

        // Execute
        // Assert
        assertThrows(InvalidPlayerMoveException.class, () -> client.makeMove(AUTH_KEY, GAME_ID, Tile.X, 0, 0));
    }
}