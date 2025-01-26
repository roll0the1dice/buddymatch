package com.example.buddy_match.service.baseService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@RestControllerAdvice
public class TeamUserNotFoundAdvice {
    @ExceptionHandler(TeamUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String TeamUserNotFoundHandler(TeamUserNotFoundException ex) {
        return ex.getMessage();
    }
}