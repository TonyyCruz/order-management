package com.anthony.blacksmithOnlineStore.exceptions;

import com.anthony.blacksmithOnlineStore.exceptions.baseExceptions.NotFoundException;

public class BlacksmithNotFoundException extends NotFoundException {
  public BlacksmithNotFoundException(Long id) {
    super("Blacksmith not found: " + id);
  }
}
