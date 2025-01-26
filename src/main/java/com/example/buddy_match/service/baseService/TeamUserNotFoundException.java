package com.example.buddy_match.service.baseService;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
public class TeamUserNotFoundException extends RuntimeException {
    public TeamUserNotFoundException() {
    }

    public TeamUserNotFoundException(Long id) {
        super("Could not find TeamUser " + id);
    }
}