package org.it.me.colin.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.it.me.colin.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.it.me.colin.TestConstants.*;
import static org.it.me.colin.util.GameUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameUtilsTest {

    private static final List<GameTile> DIAGONAL_X_GAME_BOARD = ImmutableList.of(
            GameTile.builder().tile(Tile.X).x(0).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
            GameTile.builder().tile(Tile.X).x(1).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
            GameTile.builder().tile(Tile.X).x(2).y(2).build());
    private static final GameResponse DIAGONAL_X_GAME_RESPONSE = GameResponse.builder()
            .gameId(GAME_ID)
            .gameBoard(DIAGONAL_X_GAME_BOARD)
            .status(Status.NONE)
            .winner(Winner.NONE)
            .build();

    @Test
    void transformTo2dMap_HappyCase_Success() {
        // Setup
        // Execute
        Map<Integer, Map<Integer, Tile>> actualGameMap = transformGameBoardTo2dMap(DIAGONAL_X_GAME_RESPONSE.getGameBoard());

        // Assert
        assertEquals(GAME_MAP, actualGameMap);
    }

    @Test
    void renderGameMapToString_HappyCase_Success() {
        // Setup
        // Execute
        String actualGameBoardString = renderGameMapToString(GAME_MAP, BOARD_WIDTH, BOARD_HEIGHT);

        // Assert
        assertEquals(GAME_BOARD_STRING, actualGameBoardString);
    }

    @Test
    void renderGameMapToString_MissingTile_RenderMissingTileAsEmpty() {
        // Setup
        Map<Integer, Map<Integer, Tile>> gameMapMissingTile = ImmutableMap.of(
                0, ImmutableMap.of(0, Tile.X, 1, Tile.X, 2, Tile.X),
                1, ImmutableMap.of(0, Tile.X, 1, Tile.X, 2, Tile.X),
                2, ImmutableMap.of(0, Tile.X, 1, Tile.X));

        // Execute
        String actualGameBoardStringWithEmptyTile = renderGameMapToString(gameMapMissingTile, BOARD_WIDTH, BOARD_HEIGHT);

        // Assert
        assertEquals("X|X|X\n-----\nX|X|X\n-----\nX|X| \n", actualGameBoardStringWithEmptyTile);
    }

    private static Stream<Arguments> validCoordinateInputs() {
        return Stream.of(
                Arguments.of("0,0", new ImmutablePair<>(0,0)),
                Arguments.of("0, 0", new ImmutablePair<>(0,0)),
                Arguments.of("0,\n0", new ImmutablePair<>(0,0)));
    }

    @ParameterizedTest
    @MethodSource("validCoordinateInputs")
    void parseCoordinates_ValidInput_Success(String coordinateInput, Pair<Integer, Integer> expectedPair) {
        // Setup
        // Execute
        Pair<Integer, Integer> actualPair = parseCoordinates(coordinateInput);

        // Assert
        assertEquals(expectedPair, actualPair);
    }

    private static Stream<Arguments> invalidCoordinateInputs() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("0,0,0"),
                Arguments.of("0,"));
    }

    @ParameterizedTest
    @MethodSource("invalidCoordinateInputs")
    void parseCoordinates_InvalidInput_ThrowsIllegalArgumentException(String coordinateInput) {
        // Setup
        // Execute
        // Assert
        assertThrows(IllegalArgumentException.class, () -> parseCoordinates(coordinateInput));
    }

    private static Stream<Arguments> determinePlayerOrder_GameResponses() {
        return Stream.of(
                Arguments.of(GameResponse.builder().status(Status.NONE).build(), ImmutableList.of(Tile.X, Tile.O)),
                Arguments.of(GameResponse.builder()
                        .status(Status.ACTIVE)
                        .gameBoard(ImmutableList.of(
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(2).build())).build(),
                        ImmutableList.of(Tile.X, Tile.O)),
                Arguments.of(GameResponse.builder()
                        .status(Status.ACTIVE)
                        .gameBoard(ImmutableList.of(
                                GameTile.builder().tile(Tile.X).x(0).y(0).build(),
                                GameTile.builder().tile(Tile.O).x(0).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(2).build())).build(),
                        ImmutableList.of(Tile.X, Tile.O)),
                Arguments.of(GameResponse.builder()
                        .status(Status.ACTIVE)
                        .gameBoard(ImmutableList.of(
                                GameTile.builder().tile(Tile.X).x(0).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(2).build())).build(),
                        ImmutableList.of(Tile.O, Tile.X)),
                Arguments.of(GameResponse.builder()
                        .status(Status.ACTIVE)
                        .gameBoard(ImmutableList.of(
                                GameTile.builder().tile(Tile.O).x(0).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
                                GameTile.builder().tile(Tile.EMPTY).x(2).y(2).build())).build(),
                        ImmutableList.of(Tile.X, Tile.O)));
    }

    @ParameterizedTest
    @MethodSource("determinePlayerOrder_GameResponses")
    void determinePlayerOrder_HappyCase_Success(GameResponse gameResponse, List<Tile> expectedPlayerOrder) {
        // Setup
        // Execute
        List<Tile> actualPlayerOrder = determinePlayerOrder(gameResponse);

        // Assert
        assertEquals(expectedPlayerOrder, actualPlayerOrder);
    }
}