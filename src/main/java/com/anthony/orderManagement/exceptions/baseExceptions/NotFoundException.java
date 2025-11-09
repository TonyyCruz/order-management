package com.anthony.orderManagement.exceptions.baseExceptions;

public class NotFoundException extends RuntimeException{
  protected NotFoundException(String message) {
    super(message);
  }
}
