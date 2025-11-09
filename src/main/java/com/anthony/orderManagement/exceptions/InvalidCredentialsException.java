package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.ArgumentException;

public class InvalidCredentialsException extends ArgumentException {
  public InvalidCredentialsException() {
    super("Invalid credentials provided");
  }
}
