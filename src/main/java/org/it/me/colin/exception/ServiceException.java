package org.it.me.colin.exception;

/**
 * Service exceptions are typically 500 HTTP status codes or other service side issues that can be retried.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
