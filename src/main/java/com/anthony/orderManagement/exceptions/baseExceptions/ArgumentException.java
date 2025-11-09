package com.anthony.orderManagement.exceptions.baseExceptions;

public class ArgumentException extends RuntimeException{
  protected ArgumentException(String message) {
    super(message);
  }
}
