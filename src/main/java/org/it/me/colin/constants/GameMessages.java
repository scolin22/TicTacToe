package org.it.me.colin.constants;

public class GameMessages {
    public static final String INITIALIZE_GAME_MESSAGE = "Enter game id to continue from an existing game or leave blank to begin a new one.";
    public static final String NEW_GAME_MESSAGE = "Started a new game with id: %s";
    public static final String GAME_ENDED_MESSAGE = "Game is already over.";
    public static final String GAME_FOUND_MESSAGE = "Game was found. Resuming...";
    public static final String GAME_NOT_FOUND_MESSAGE = "The game was not found.";
    public static final String PLAYER_MOVE_MESSAGE = "Enter move for player \"%s\": ";
    public static final String PLAYER_ANOTHER_MOVE_MESSAGE = "Move was invalid. Enter another move.";
    public static final String PLAYER_WON_MESSAGE = "Player \"%s\" wins!";
    public static final String NOBODY_WINS_MESSAGE = "Nobody wins!";
    public static final String INVALID_COORDINATE_LENGTH_MESSAGE = "Exactly 2 comma-separated values must be provided as coordinates. Input: %s";
    public static final String INVALID_COORDINATE_NON_INTEGER_MESSAGE = "Integers must be provided as coordinates. Input: %s";
}
