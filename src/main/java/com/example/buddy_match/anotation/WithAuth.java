package com.example.buddy_match.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.buddy_match.enums.UserRoleEnum;

/**
 * This is a generated BaseService for demonstration purposes.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithAuth {
    /** This is an example modelAssembler. */
    UserRoleEnum mustRole();
}