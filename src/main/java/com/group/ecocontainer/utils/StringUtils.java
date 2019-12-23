package com.group.ecocontainer.utils;

public class StringUtils {

  public static boolean isNull(String str) {
    return str == null;
  }
  public static boolean isEmpty(String str) {
    return str.isEmpty();
  }

  public static boolean isNullOrEmpty(String str) {
    return isNull(str) || isEmpty(str);
  }
}
