package org.it.me.colin.exception;

/**
 * Client exceptions are typically 400 HTTP status codes or other client side issues that can not be retried.
 */
public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
    public ClientException(Throwable throwable) {
        super(throwable);
    }
}
