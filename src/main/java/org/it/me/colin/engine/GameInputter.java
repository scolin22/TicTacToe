package org.it.me.colin.engine;

import org.apache.commons.lang3.tuple.Pair;

public interface GameInputter {
    /**
     * Get the players next move from an input and returns as a pair of x,y coordinates.
     * @return The x,y coordinates of the player's move.
     */
    Pair<Integer, Integer> getPlayerMove();

    /**
     * Get a game id from an input.
     * @return The inputted game id.
     */
    String getGameId();
}
