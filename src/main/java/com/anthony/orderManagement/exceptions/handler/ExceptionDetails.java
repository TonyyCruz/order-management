package com.anthony.orderManagement.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails implements Serializable {
  private String title;
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",
      timezone = "GMT")
  private Instant timestamp;
  private int status;
  private String exception;
  private String path;
  @Setter(AccessLevel.NONE)
  private final Map<String, String> errors = new HashMap<>();

  public void addError(String error, String details) {
    errors.put(error, details);
  }
}
