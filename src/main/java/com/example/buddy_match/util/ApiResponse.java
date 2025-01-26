package com.example.buddy_match.util;

import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * This is a generated BaseService for demonstration purposes.
 */
@Data
public class ApiResponse<T>  {
    /** This is an example. */
    private Integer statusCodeValue;

    /** This is an example. */
    private String statusCode;

    /** This is an example. */
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(Integer statusCodeValue, String statusCode, T data) {
        this.statusCodeValue = statusCodeValue;
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, "success", data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(Integer statusCodeValue, String statusCode, T data) {
        return ResponseEntity.ok().body(new ApiResponse<>(statusCodeValue, statusCode, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(Integer statusCodeValue, String statusCode) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(statusCodeValue, statusCode, null));
    }
}