package com.example.buddy_match.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@RestControllerAdvice
@Hidden
public class BuddyUserNotFoundAdvice {
    @ExceptionHandler(BuddyUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BuddyUserNotFoundHandler(BuddyUserNotFoundException ex) {
        return ex.getMessage();
    }
}