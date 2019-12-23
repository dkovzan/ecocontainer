package com.group.ecocontainer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

@Log4j
public class FileUtils {
  private FileUtils() {
    super();
  }

  private static final Logger log = Logger.getLogger(FileUtils.class);

  public static File getFile(final String filepath) {
    validateFilePath(filepath);
    return new File(filepath);
  }

  public static void validateFilePath(String filepath) {
    if (StringUtils.isNullOrEmpty(filepath)) {
      String message = "[FileUtils] Specified file path is NULL or empty!";
      IllegalArgumentException e = new IllegalArgumentException(message);
      log.error(message, e);
      throw e;
    }
  }

  public static boolean isFileExist(final String filepath) {
    final File file = getFile(filepath);
    if (file.exists() && !file.isDirectory()) {
      return true;
    }
    return false;
  }

  public static boolean isFileExist(final File file) {
    if (file.exists() && !file.isDirectory()) {
      return true;
    }
    return false;
  }

  public static byte[] readAsBytes(final String filepath) {
    try {
      return Files.readAllBytes(Paths.get(validatePath(filepath)));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return new byte[]{};
  }

  public static String readAsString(final String filepath) {
    return new String(readAsBytes(filepath));
  }

  public static FileInputStream readAsStream(final String filepath) {
    try {
      return new FileInputStream(validatePath(filepath));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static String validatePath(final String filepath) {
    validateFilePath(filepath);
    if (!isFileExist(filepath)) {
      throw new IllegalArgumentException("[FileUtils] Specified file [" + filepath + "] is NOT exist or NOT found on file system!");
    }
    return filepath;
  }
}
