package org.it.me.colin.exception;

/**
 * This exception indicates that the player has made an invalid move.
 */
public class InvalidPlayerMoveException extends RuntimeException {
    public InvalidPlayerMoveException(String message) {
        super(message);
    }
}
