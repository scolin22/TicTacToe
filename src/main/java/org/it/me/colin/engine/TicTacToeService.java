package org.it.me.colin.engine;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.it.me.colin.client.TicTacToeClient;
import org.it.me.colin.exception.GameNotFoundException;
import org.it.me.colin.exception.InvalidPlayerMoveException;
import org.it.me.colin.model.GameResponse;
import org.it.me.colin.model.Tile;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TicTacToeService {

    private final TicTacToeClient client;

    private String authKey;

    public GameResponse createGame() {
        initializeAuthenticationKey();
        return client.createGame(authKey);
    }

    public GameResponse getGame(String gameId) throws GameNotFoundException {
        initializeAuthenticationKey();
        return client.getGame(authKey, gameId);
    }


    public GameResponse makeMove(String gameId, Tile tile, int x, int y) throws InvalidPlayerMoveException {
        initializeAuthenticationKey();
        return client.makeMove(authKey, gameId, tile, x, y);
    }

    private void initializeAuthenticationKey() {
        if (this.authKey != null) {
            return;
        }

        String authKey = client.getAuthenticationKey();

        if (client.validateAuthenticationKey(authKey)) {
            this.authKey = authKey;
        } else {
            throw new IllegalArgumentException("Authentication key validation failed.");
        }
    }
}
