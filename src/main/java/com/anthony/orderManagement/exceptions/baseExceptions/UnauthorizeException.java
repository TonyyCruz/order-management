package com.anthony.orderManagement.exceptions.baseExceptions;

public class UnauthorizeException extends RuntimeException{
  protected UnauthorizeException(String message) {
    super(message);
  }
}
