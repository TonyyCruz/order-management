package com.anthony.blacksmithOnlineStore.exceptions;

import com.anthony.blacksmithOnlineStore.exceptions.baseExceptions.NotFoundException;

public class BlacksmithNotFoundException extends NotFoundException {
  public BlacksmithNotFoundException() {
    super("Blacksmith not found");
  }
}
