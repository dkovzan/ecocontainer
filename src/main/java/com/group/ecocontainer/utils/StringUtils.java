package com.group.ecocontainer.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;

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

  public static boolean matchRange(String rangeByComma, long value) {
    String[] range = rangeByComma.split(",");
    return value >= Long.valueOf(range[0]) &&  value <= Long.valueOf(range[1]);
  }

  public static Timestamp stringToTimestamp(String str) {
    if (str.contains("T")) {
      str = str.replace("T", " ");
    }
    return Timestamp.valueOf(str);
  }
}
