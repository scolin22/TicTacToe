package org.it.me.colin.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.it.me.colin.constants.GameMessages;
import org.it.me.colin.model.GameTile;
import org.it.me.colin.model.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUtils {

    /**
     * Transforms a list of {@link GameTile} objects to a 2D map where the first key is the x coordinate and the second
     * key is the y coordinate. The nested value is the {@link Tile}.
     * Duplicated tiles at the same coordinates will be overwritten by the last appearing tile.
     * @param gameBoard A list of {@link GameTile} objects.
     * @return Returns a nested map representing the gameboard.
     * e.g. {0 => {0 => Tile.NONE, 1 => Tile.NONE, 2 => Tile.NONE},
     *       1 => {0 => Tile.NONE, 1 => Tile.NONE, 2 => Tile.NONE},
     *       2 => {0 => Tile.NONE, 1 => Tile.NONE, 2 => Tile.NONE}}
     */
    public static Map<Integer, Map<Integer, Tile>> transformGameBoardTo2dMap(List<GameTile> gameBoard) {
        Map<Integer, Map<Integer, Tile>> gameMap = new HashMap<>();
        for (GameTile boardPiece : gameBoard) {
            if (!gameMap.containsKey(boardPiece.getX())) {
                gameMap.put(boardPiece.getX(), new HashMap<>());
            }
            gameMap.get(boardPiece.getX()).put(boardPiece.getY(), boardPiece.getTile());
        }
        return gameMap;
    }

    /**
     * Renders a 2D map representation of the game board into a string. This method validates that the each coordinate
     * for 0 <= x < boardWidth and 0 <= y < boardHeight is populated. If an tile is not present in the gameMap then it
     * is treated as {@link Tile#EMPTY}.
     * @param gameMap The 2D map representation of the game board to render. See {@link #transformGameBoardTo2dMap(List)}
     * @param boardWidth The width of the board.
     * @param boardHeight The height of the board.
     * @return A string render of the game board.
     */
    public static String renderGameMapToString(Map<Integer, Map<Integer, Tile>> gameMap,
                                               int boardWidth,
                                               int boardHeight) throws IllegalArgumentException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < boardWidth; y++) {
            for (int x = 0; x < boardHeight; x++) {
                Tile tile = gameMap.containsKey(x) ? gameMap.get(x).getOrDefault(y, Tile.EMPTY) : Tile.EMPTY;

                // Render the tile
                switch (tile) {
                    case X:
                        stringBuilder.append("X");
                        break;
                    case O:
                        stringBuilder.append("O");
                        break;
                    case EMPTY:
                    default:
                        // TODO: Optimize future calls by omitting EMPTY tiles from response
                        stringBuilder.append(" ");
                        break;
                }

                // Render the vertical divider or a new row
                if (x == boardHeight - 1) {
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append("|");
                }
            }

            // Render the horizontal divider
            if (y != boardWidth - 1) {
                stringBuilder.append("-----\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Parse the user input string and returns the pair of integer coordinates as x,y.
     * @param input The user input string. e.g. "1,2"
     * @return A pair of integer coordinates as x,y.
     * @throws IllegalArgumentException Thrown if there are not exactly 2 coordinates found in the input.
     * @throws IllegalArgumentException Thrown if either coordinates found in the input are not integers.
     */
    public static Pair<Integer, Integer> parseCoordinates(String input) {
        String sanitizedInput = StringUtils.deleteWhitespace(input);
        String[] coordinates = StringUtils.split(sanitizedInput, ",");

        if (coordinates.length != 2) {
            throw new IllegalArgumentException(String.format(GameMessages.INVALID_COORDINATE_LENGTH_MESSAGE, input));
        }

        try {
            return new ImmutablePair<>(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(GameMessages.INVALID_COORDINATE_NON_INTEGER_MESSAGE, input));
        }
    }
}
