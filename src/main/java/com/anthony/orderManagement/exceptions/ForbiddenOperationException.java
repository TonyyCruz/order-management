package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.ForbiddenException;

public class ForbiddenOperationException extends ForbiddenException {

  public ForbiddenOperationException() {
    super("You are not authorized to perform this operation.");
  }

  public ForbiddenOperationException(String msg) {
    super(msg);
  }
}
