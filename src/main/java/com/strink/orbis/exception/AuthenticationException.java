package com.strink.orbis.exception;

/**
 * Exception thrown when authentication fails.
 * This exception is mapped to HTTP 401 UNAUTHORIZED status.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
