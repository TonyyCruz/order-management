package com.anthony.orderManagement.exceptions;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException() {
    super("Invalid or expired token");
  }
}
