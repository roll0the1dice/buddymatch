package com.example.buddy_match.exception;

/**
 * This is a generated ServerException for demonstration purposes.
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(String.format("Code = %s, Message = %s",code,message));
    }
}