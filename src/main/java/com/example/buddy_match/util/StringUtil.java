package com.example.buddy_match.util;

public class StringUtil {
  public static boolean containsSpace(String str) {
      if (str == null || str.isEmpty()) {
          return false; // 如果字符串为空或 null，不包含空格
      }
      return str.contains(" ");
  }
}

