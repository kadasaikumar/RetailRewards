package com.chc.retail.exception;

public class TransactionNotFoundException extends RuntimeException {
  public TransactionNotFoundException(String message) {
    super(message);
  }
}
