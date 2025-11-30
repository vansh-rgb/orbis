package com.strink.orbis.exception;

/**
 * Exception thrown when validation fails.
 * This exception is mapped to HTTP 400 BAD REQUEST status.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String field, String reason) {
        super(String.format("Validation failed for %s: %s", field, reason));
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
