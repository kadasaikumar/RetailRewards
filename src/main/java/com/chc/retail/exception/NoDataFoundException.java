package com.chc.retail.exception;

public class NoDataFoundException extends RuntimeException {
  public NoDataFoundException() {
    super("No data found");
  }
}
