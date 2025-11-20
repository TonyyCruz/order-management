package com.anthony.orderManagement.exceptions.handler;

import com.anthony.orderManagement.exceptions.baseExceptions.BadRequestException;
import com.anthony.orderManagement.exceptions.baseExceptions.NotFoundException;
import com.anthony.orderManagement.exceptions.baseExceptions.ForbiddenException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(e.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<String> handleUnauthorizedException(ForbiddenException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(e.getMessage());
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> defaultException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Ops, algo deu errado. 😵");
  }
}
