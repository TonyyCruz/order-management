package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.BadRequestException;

public class InvalidCredentialsException extends BadRequestException {
  public InvalidCredentialsException() {
    super("Invalid credentials provided");
  }
}
