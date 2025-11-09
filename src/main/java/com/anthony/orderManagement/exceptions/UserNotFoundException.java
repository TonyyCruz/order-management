package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super("User not found exception.");
  }
}
