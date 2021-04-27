package org.it.me.colin.engine;

import org.it.me.colin.model.GameTile;

import java.util.List;

public interface GameRenderer {
    /**
     * Renders the rectangular game board with a given width and height.
     * @param gameBoard A list of game tiles to render.
     * @param boardWidth The rendered width of the game board.
     * @param boardHeight The rendered height of the game board.
     */
    void renderGameBoard(List<GameTile> gameBoard, int boardWidth, int boardHeight) ;

    /**
     * TODO:
     * @param message
     */
    void renderGameMessage(String message);
}
