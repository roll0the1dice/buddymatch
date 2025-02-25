package com.example.buddy_match.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This is a generated assignNonNullValues for demonstration purposes.
 */
public class ObjectAssigner {
    public static <T> void assignNonNullValues(T source, T target) {
        if (source == null || target == null) {
            return; 
        }
        
        Class<?> clazz = source.getClass();
        
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            
            for (Field field : fields) {
                String fieldName = field.getName();
                String getterName = "get" + capitalize(fieldName);
                String setterName = "set" + capitalize(fieldName);
                try {
                    Method getter = clazz.getDeclaredMethod(getterName);
                    getter.setAccessible(true);
                    Method setter = clazz.getDeclaredMethod(setterName, field.getType());
                    setter.setAccessible(true);
                    Object value = getter.invoke(source);
                    if (value != null) {
                        setter.invoke(target, value);
                    }
                } catch (NoSuchMethodException e) {
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}