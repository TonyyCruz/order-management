package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.ArgumentException;

public class UsernameAlreadyExistsException extends ArgumentException {
  public UsernameAlreadyExistsException() {
    super("Username already exists");
  }
}
