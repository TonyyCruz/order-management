package com.anthony.orderManagement.exceptions;

import com.anthony.orderManagement.exceptions.baseExceptions.UnauthorizedException;

public class UnauthorizedOperationException extends UnauthorizedException {

  public UnauthorizedOperationException() {
    super("You are not authorized to perform this operation.");
  }

  public UnauthorizedOperationException(String msg) {
    super(msg);
  }
}
