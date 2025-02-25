package com.example.buddy_match.exception;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
    }

    public TeamNotFoundException(Long id) {
        super("Could not find Team " + id);
    }
}