package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.BadRequestException;

public class UsernameAlreadyExistsException extends BadRequestException {
  public UsernameAlreadyExistsException() {
    super("Username already exists");
  }
}
