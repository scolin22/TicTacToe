package org.it.me.colin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.it.me.colin.model.*;

import java.util.List;
import java.util.Map;

public class TestConstants {
    public static final String GAME_ID = "96488d2e362eeb9070bd0769a008abaaf2e5297d";
    public static final List<GameTile> GAME_BOARD = ImmutableList.of(
            GameTile.builder().tile(Tile.X).x(0).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
            GameTile.builder().tile(Tile.X).x(1).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
            GameTile.builder().tile(Tile.X).x(2).y(2).build());
    public static final GameResponse GAME_RESPONSE = GameResponse.builder()
            .gameId(GAME_ID)
            .gameBoard(GAME_BOARD)
            .status(Status.NONE)
            .winner(Winner.NONE)
            .build();
    public static final int BOARD_WIDTH = 3;
    public static final int BOARD_HEIGHT = 3;
    public static final Map<Integer, Map<Integer, Tile>> GAME_MAP = ImmutableMap.of(
            0, ImmutableMap.of(0, Tile.X, 1, Tile.EMPTY, 2, Tile.EMPTY),
            1, ImmutableMap.of(0, Tile.EMPTY, 1, Tile.X, 2, Tile.EMPTY),
            2, ImmutableMap.of(0, Tile.EMPTY, 1, Tile.EMPTY, 2, Tile.X));
    public static final String GAME_BOARD_STRING = "X| | \n-----\n |X| \n-----\n | |X\n";
}
