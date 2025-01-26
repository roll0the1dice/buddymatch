package com.example.buddy_match.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String SPECIAL_CHAR_REGEX = "^[a-zA-Z0-9]*$";  // 只允许字母和数字

    // 验证用户名和密码是否包含特殊字符
    public static boolean hasSpecialCharacters(String input) {
        return !Pattern.matches(SPECIAL_CHAR_REGEX, input);
    }
}
