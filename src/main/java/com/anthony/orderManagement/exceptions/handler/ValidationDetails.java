package com.anthony.orderManagement.exceptions.handler;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationDetails extends ExceptionDetails {
  private final List<FieldErrorMessage> fieldError = new ArrayList<>();

  public void addFieldError(String field, String error) {
    fieldError.add(new FieldErrorMessage(field, error));
  }
}
