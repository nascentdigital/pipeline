package com.nascentdigital.pipeline;

/**
 * Thrown when a {@link Pipeline} predicate cannot find an expected element in the sequence.
 */
public class NoElementFoundException extends RuntimeException {

    public NoElementFoundException() {
        super();
    }

    public NoElementFoundException(String message) {
        super(message);
    }

    public NoElementFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoElementFoundException(Throwable cause) {
        super(cause);
    }
}
