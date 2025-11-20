package com.anthony.orderManagement.exceptions.baseExceptions;

public class ForbiddenException extends RuntimeException{
  protected ForbiddenException(String message) {
    super(message);
  }
}
