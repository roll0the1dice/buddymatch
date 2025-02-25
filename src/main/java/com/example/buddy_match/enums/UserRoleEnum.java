package com.example.buddy_match.enums;

/**
 * This is a generated UserRoleEnum for demonstration purposes.
 */
public enum UserRoleEnum {

     USER("user", 0), ADMIN("admin", 1), BAN("baned user", 2);

    /** This is an example UserRoleEnum. */
    private String description;

    /** This is an example UserRoleEnum. */
    private Integer value;

    UserRoleEnum() {
    }

    UserRoleEnum(String description, Integer value) {
        this.description = description;
        this.value = value;
    }

    String getDescription() {
        return description;
    }

    Integer getValue() {
        return value;
    }
}