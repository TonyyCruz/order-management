package com.anthony.orderManagement.exceptions.baseExceptions;

public class UnauthorizedException extends RuntimeException{
  protected UnauthorizedException(String message) {
    super(message);
  }
}
