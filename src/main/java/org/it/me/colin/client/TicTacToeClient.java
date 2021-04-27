package org.it.me.colin.client;

import org.it.me.colin.exception.GameNotFoundException;
import org.it.me.colin.exception.InvalidPlayerMoveException;
import org.it.me.colin.model.GameResponse;
import org.it.me.colin.model.Tile;

public interface TicTacToeClient {
    /**
     * Generate an authentication key
     * @return The authentication key
     */
    String getAuthenticationKey();

    /**
     * Validate the authentication key.
     * @param authKey The authentication key to be validated.
     * @return Whether the authentication key is valid.
     */
    boolean validateAuthenticationKey(String authKey);

    /**
     * Creates a new game of TicTacToe.
     * @return The game state of the newly created game.
     */
    GameResponse createGame(String authKey);

    /**
     * Describes the specified game.
     * @param gameId The identifier of the game to be described.
     * @return The game state of the specified game.
     */
    GameResponse getGame(String authKey, String gameId) throws GameNotFoundException;

    /**
     * Make a move with a tile at coordinates in the specified game.
     * @param gameId The identifier of the game to play.
     * @param tile The tile to place.
     * @param x The x co-ordinate where the tile is placed.
     * @param y The y co-ordinate where the tile is placed.
     * @return The game state after the move.
     * @throws InvalidPlayerMoveException Thrown if the move was invalid.
     */
    GameResponse makeMove(String authKey, String gameId, Tile tile, int x, int y) throws InvalidPlayerMoveException;
}
