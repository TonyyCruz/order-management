package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.UnauthorizeException;

public class UnauthorizedOperationException extends UnauthorizeException {

  public UnauthorizedOperationException() {
    super("You are not authorized to perform this operation.");
  }

  public UnauthorizedOperationException(String msg) {
    super(msg);
  }
}
