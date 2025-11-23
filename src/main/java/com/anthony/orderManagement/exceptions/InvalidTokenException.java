package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
  public InvalidTokenException() {
    super("Invalid or expired token");
  }

  public InvalidTokenException(Throwable cause) {
    super("Invalid or expired token", cause);
  }
}
