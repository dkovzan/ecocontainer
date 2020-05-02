package com.group.econtainer.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
  private FileUtils() {
    super();
  }

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

  public static File getFile(final String filepath) {
    validateFilePath(filepath);
    return new File(filepath);
  }

  public static void validateFilePath(String filepath) {
    if (StringUtils.isNullOrEmpty(filepath)) {
      String message = "[FileUtils] Specified file path is NULL or empty!";
      IllegalArgumentException e = new IllegalArgumentException(message);
      logger.error(message, e);
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

  public static File createFile(final String filepath) {
    final File file = getFile(filepath);
    if (!isFileExist(file)) {
      try {
        if (file.createNewFile()) {
          logger.info("[FileUtils] New file created: [" + filepath + "].");
        }
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
    } else {
      logger.warn("[FileUtils] File [" + filepath + "] is NOT created: file already exists!");
    }
    return file;
  }

  public static byte[] readAsBytes(final String filepath) {
    try {
      return Files.readAllBytes(Paths.get(validatePath(filepath)));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return new byte[]{};
  }

  public static String readAsString(final String filepath) {
    return new String(readAsBytes(filepath));
  }

  public static void writeToFile(final String filepath, final byte[] content) {
    try (FileOutputStream fos = new FileOutputStream(validatePath(filepath))) {
      fos.write(content);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public static String validatePath(final String filepath) {
    validateFilePath(filepath);
    if (!isFileExist(filepath)) {
      throw new IllegalArgumentException("[FileUtils] Specified file [" + filepath + "] is NOT exist or NOT found on file system!");
    }
    return filepath;
  }
}
