package com.example.buddy_match.util;

import com.example.buddy_match.exception.BusinessException;
import com.example.buddy_match.exception.ErrorCode;

/**
 * This is a generated BaseService for demonstration purposes.
 */
public class ThrowUtils {
    public static void throwIf(Boolean condition, RuntimeException runtimeException) {
        if (condition) throw runtimeException;
    }

    public static void throwIf(Boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode.toString()));
    }

    public static void throwIf(Boolean condition, Integer code, String message) {
        throwIf(condition, new BusinessException(code, message));
    }
}