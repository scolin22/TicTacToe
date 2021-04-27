package org.it.me.colin.util;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.it.me.colin.model.Tile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.it.me.colin.TestConstants.*;
import static org.it.me.colin.util.GameUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameUtilsTest {

    @Test
    void transformTo2dMap_HappyCase_Success() {
        // Setup
        // Execute
        Map<Integer, Map<Integer, Tile>> actualGameMap = transformGameBoardTo2dMap(GAME_RESPONSE.getGameBoard());

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
                Arguments.of("0,\n0", new ImmutablePair<>(0,0))
        );
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
                Arguments.of("0,")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCoordinateInputs")
    void parseCoordinates_InvalidInput_ThrowsIllegalArgumentException(String coordinateInput) {
        // Setup
        // Execute
        // Assert
        assertThrows(IllegalArgumentException.class, () -> parseCoordinates(coordinateInput));
    }
}