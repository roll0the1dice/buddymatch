package com.example.buddy_match.service.baseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This is a generated NotFoundException for demonstration purposes.
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> GlobalExceptionAdvice(RuntimeException ex) {
        return ResponseEntity.status(500).body("Internal Server Error: " + ex.getMessage());
    }

    @ExceptionHandler(com.example.buddy_match.service.baseService.BadRequestException.class)
    public ResponseEntity<?> GlobalExceptionAdvice(BadRequestException ex) {
        return ResponseEntity.badRequest().body("Bad Request Error: " + ex.getMessage());
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> GlobalExceptionAdvice(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Input Error: " + ex.getMessage());
    }
}