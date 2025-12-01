package com.anthony.blacksmithOnlineStore.exceptions;

import com.anthony.blacksmithOnlineStore.exceptions.baseExceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super("User not found");
  }
}
