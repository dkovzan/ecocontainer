package com.econtainer.base.exception;

public class CsvDaoException extends Exception {

  public CsvDaoException() {}

  public CsvDaoException(String message) {
    super(message);
  }

  public CsvDaoException(String message, Throwable cause) {
    super(message, cause);
  }

}
