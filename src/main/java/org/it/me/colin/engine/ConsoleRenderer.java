package org.it.me.colin.engine;

import lombok.extern.log4j.Log4j2;
import org.it.me.colin.model.GameTile;
import org.it.me.colin.model.Tile;
import org.it.me.colin.util.GameUtils;

import java.util.List;
import java.util.Map;

@Log4j2
public class ConsoleRenderer implements GameRenderer {
    public void renderGameBoard(List<GameTile> gameBoard, int boardWidth, int boardHeight)  {
        // Transform into 2D Map
        log.debug(String.format("Rendering gameBoard: %s boardWidth: %s boardHeight: %s", gameBoard, boardWidth, boardHeight));
        Map<Integer, Map<Integer, Tile>> gameBoardMap = GameUtils.transformGameBoardTo2dMap(gameBoard);

        // Render 2D Map
        String gameBoardString = GameUtils.renderGameMapToString(gameBoardMap, boardWidth, boardHeight);

        System.out.println(gameBoardString);
    }

    public void renderGameMessage(String message) {
        log.debug(String.format("Rendering message: %s", message));
        System.out.println(message);
    }
}
