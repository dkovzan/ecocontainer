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

  public static boolean matchRange(String rangeByComma, int value) {
    String[] range = rangeByComma.split(",");
    return value >= Integer.valueOf(range[0]) ||  value <= Integer.valueOf(range[1]);
  }
}
