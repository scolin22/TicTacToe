package org.it.me.colin.engine;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.it.me.colin.exception.GameNotFoundException;
import org.it.me.colin.exception.InvalidPlayerMoveException;
import org.it.me.colin.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.it.me.colin.constants.GameMessages.*;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GameInstance {
    private static final int BOARD_WIDTH = 3;
    private static final int BOARD_HEIGHT = 3;

    private final GameRenderer gameRenderer;
    private final GameInputter gameInputter;
    private final TicTacToeService service;

    public void run() {
        log.info("Running game");

        GameResponse gameResponse = initializeOrResumeGame();
        log.info(String.format("Game id: %s", gameResponse.getGameId()));

        mainLoop(gameResponse);
    }

    /**
     * Prompts for a game id to resume an existing game or a blank one to begin a new one. If an game id is provided
     * and not found, then for another game id.
     * @return The game state of the resumed game or newly created one.
     */
    private GameResponse initializeOrResumeGame() {
        while (true) {
            gameRenderer.renderGameMessage(INITIALIZE_GAME_MESSAGE);
            String gameId = gameInputter.getGameId();
            log.info(String.format("Inputted game id: %s", gameId));

            if (StringUtils.isBlank(gameId)) {
                log.info("Game id was blank. Creating a new game.");
                GameResponse gameResponse = service.createGame();
                gameRenderer.renderGameMessage(String.format(NEW_GAME_MESSAGE, gameResponse.getGameId()));
                return gameResponse;
            }

            try {
                GameResponse gameResponse = service.getGame(gameId);
                log.info("Game id found.");

                if (!Status.COMPLETE.equals(gameResponse.getStatus())) {
                    gameRenderer.renderGameMessage(GAME_FOUND_MESSAGE);
                    return gameResponse;
                }

                gameRenderer.renderGameMessage(GAME_ENDED_MESSAGE);
            } catch (GameNotFoundException e) {
                log.info("Game id was not found. Prompting for another one.");
                gameRenderer.renderGameMessage(GAME_NOT_FOUND_MESSAGE);
            }
        }
    }

    /**
     * Iterates between all players to make their moves until the game is over. Renders the game board between player
     * moves.
     * @param gameResponse The current game state.
     */
    private void mainLoop(GameResponse gameResponse) {
        log.info("Rendering initial game board.");
        gameRenderer.renderGameBoard(gameResponse.getGameBoard(), BOARD_WIDTH, BOARD_HEIGHT);

        // determine first player order
        List<Tile> players = determinePlayerOrder(gameResponse);

        while (!Status.COMPLETE.equals(gameResponse.getStatus())) {
            for (Tile player : players) {
                log.info(String.format("Player \"%s\" turn.", player));
                gameResponse = handlePlayerMove(gameResponse, player);

                gameRenderer.renderGameBoard(gameResponse.getGameBoard(), BOARD_WIDTH, BOARD_HEIGHT);

                // Check game end condition
                log.info("Check if game is over.");
                if (checkAndHandleGameEnd(gameResponse)) break;
            }
        }
    }

    /**
     * Returns the player order. If the game is already active then player with the least number of played moves goes
     * first. However, it's impossible to determine the player order if the played moves are even. If the played moves
     * is even or if the game has not started, then order is random.
     * @param gameResponse The current game state.
     * @return The players in playing order.
     */
    private List<Tile> determinePlayerOrder(GameResponse gameResponse) {
        log.info("Determining player order.");
        if (Status.ACTIVE.equals(gameResponse.getStatus())) {
            // Count frequency of each tile
            Map<Tile, Long> tileToCounts = gameResponse.getGameBoard().stream()
                    .map(GameTile::getTile)
                    .filter(Tile.EMPTY::equals)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // If player moves are not even then player with least moves goes first
            long xFrequency = tileToCounts.getOrDefault(Tile.X, 0L);
            long oFrequency = tileToCounts.getOrDefault(Tile.O, 0L);
            if (xFrequency != oFrequency) {
                return tileToCounts.entrySet().stream()
                        .sorted(Map.Entry.<Tile, Long>comparingByValue().reversed())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }
        }

        List<Tile> players = Arrays.asList(Tile.X, Tile.O);
        Collections.shuffle(players);
        return players;
    }

    /**
     * Prompts for a move from the current player and makes the move. If an valid move is made, then prompt for another
     * move until a valid move is made.
     * @param gameResponse The current game state.
     * @param player The player to make a move.
     * @return The updated game state.
     */
    private GameResponse handlePlayerMove(GameResponse gameResponse, Tile player) {
        final String gameId = gameResponse.getGameId();
        while (true) {
            gameRenderer.renderGameMessage(String.format(PLAYER_MOVE_MESSAGE, player));
            try {
                // TODO: Validate coordinates because out of bound moves will crash the server
                Pair<Integer, Integer> coordinates = gameInputter.getPlayerMove();
                log.info(String.format("Player \"%s\" played at x: %s y: %s", player, coordinates.getLeft(), coordinates.getRight()));
                return service.makeMove(gameId, player, coordinates.getLeft(), coordinates.getRight());
            } catch (InvalidPlayerMoveException | IllegalArgumentException e) {
                log.info(String.format("Player \"%s\" played an invalid move.", player));
                gameRenderer.renderGameMessage(PLAYER_ANOTHER_MOVE_MESSAGE);
            }
        }
    }

    /**
     * Checks if the game has ended and renders an appropriate message.
     * @param gameResponse The current game state.
     * @return Whether the game has ended.
     */
    private boolean checkAndHandleGameEnd(GameResponse gameResponse) {
        if (Status.COMPLETE.equals(gameResponse.getStatus())) {
            if (Winner.TIE.equals(gameResponse.getWinner())) {
                gameRenderer.renderGameMessage(String.format(NOBODY_WINS_MESSAGE));
            } else {
                gameRenderer.renderGameMessage(String.format(PLAYER_WON_MESSAGE, gameResponse.getWinner()));
            }
            log.info(String.format("Game ended with winner %s.", gameResponse.getWinner()));
            return true;
        }

        return false;
    }
}
