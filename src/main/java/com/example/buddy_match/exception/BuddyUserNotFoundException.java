package com.example.buddy_match.exception;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
public class BuddyUserNotFoundException extends RuntimeException {
    public BuddyUserNotFoundException() {
    }

    public BuddyUserNotFoundException(Long id) {
        super("Could not find BuddyUser " + id);
    }
}