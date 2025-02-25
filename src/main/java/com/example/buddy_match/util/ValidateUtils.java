package com.example.buddy_match.util;

import java.util.regex.Pattern;

/**
 * This is a generated BaseService for demonstration purposes.
 */
public class ValidateUtils {
    /** This is an example. */
    public static final String SPECIAL_CHAR_REGEX="^[a-zA-Z0-9]*$";

    public ValidateUtils() {
    }

    public static Boolean hasSpecialCharacters(String input) {
         return !Pattern.matches(SPECIAL_CHAR_REGEX, input);
    }
}