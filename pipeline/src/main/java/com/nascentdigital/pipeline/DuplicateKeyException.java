package com.nascentdigital.pipeline;

/**
 * Thrown when a user-specified {@link Pipeline} selector fails to generate a unique key.
 */
public class DuplicateKeyException extends RuntimeException {

    public DuplicateKeyException() {
    }

    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
