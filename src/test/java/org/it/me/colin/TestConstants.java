package org.it.me.colin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.it.me.colin.model.*;

import java.util.List;
import java.util.Map;

public class TestConstants {
    public static final String AUTH_KEY = "4518dbece641f2b7b215381c242d6f71042d37bb";
    public static final String GAME_ID = "96488d2e362eeb9070bd0769a008abaaf2e5297d";
    public static final List<GameTile> GAME_BOARD = ImmutableList.of(
            GameTile.builder().tile(Tile.EMPTY).x(0).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(0).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(0).y(2).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(1).y(2).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(0).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(1).build(),
            GameTile.builder().tile(Tile.EMPTY).x(2).y(2).build());
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
    public static final String CREATE_GAME_RESPONSE_JSON = "{\"game_id\":\"" + GAME_ID + "\",\"gameboard\":[{\"tile\":\"EMPTY\",\"x\":0,\"y\":0},{\"tile\":\"EMPTY\",\"x\":0,\"y\":1},{\"tile\":\"EMPTY\",\"x\":0,\"y\":2},{\"tile\":\"EMPTY\",\"x\":1,\"y\":0},{\"tile\":\"EMPTY\",\"x\":1,\"y\":1},{\"tile\":\"EMPTY\",\"x\":1,\"y\":2},{\"tile\":\"EMPTY\",\"x\":2,\"y\":0},{\"tile\":\"EMPTY\",\"x\":2,\"y\":1},{\"tile\":\"EMPTY\",\"x\":2,\"y\":2}],\"status\":\"NONE\",\"winner\":\"NONE\"}";
    public static final String GET_GAME_RESPONSE_JSON = CREATE_GAME_RESPONSE_JSON;
    public static final String MAKE_MOVE_RESPONSE_JSON = "{\"game_id\":\"" + GAME_ID + "\",\"gameboard\":[{\"tile\":\"X\",\"x\":0,\"y\":0},{\"tile\":\"EMPTY\",\"x\":0,\"y\":1},{\"tile\":\"EMPTY\",\"x\":0,\"y\":2},{\"tile\":\"EMPTY\",\"x\":1,\"y\":0},{\"tile\":\"EMPTY\",\"x\":1,\"y\":1},{\"tile\":\"EMPTY\",\"x\":1,\"y\":2},{\"tile\":\"EMPTY\",\"x\":2,\"y\":0},{\"tile\":\"EMPTY\",\"x\":2,\"y\":1},{\"tile\":\"EMPTY\",\"x\":2,\"y\":2}],\"status\":\"NONE\",\"winner\":\"NONE\"}";
    public static final String GAME_NOT_FOUND_RESPONSE_HTML = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\"><title>404 Not Found</title><h1>Not Found</h1><p>The requested URL was not found on the server. If you entered the URL manually please check your spelling and try again.</p>";
    public static final String INVALID_MOVE_RESPONSE_HTML = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\"><title>400 Bad Request</title><h1>Bad Request</h1><p>The browser (or proxy) sent a request that this server could not understand.</p>";
}
