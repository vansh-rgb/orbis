package com.strink.orbis.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 * This exception is mapped to HTTP 409 CONFLICT status.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceType, String identifier) {
        super(String.format("%s already exists with identifier: %s", resourceType, identifier));
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
