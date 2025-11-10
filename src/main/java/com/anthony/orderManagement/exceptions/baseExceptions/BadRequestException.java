package com.anthony.orderManagement.exceptions.baseExceptions;

public class BadRequestException extends RuntimeException{
  protected BadRequestException(String message) {
    super(message);
  }
}
