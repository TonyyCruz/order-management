package com.anthony.orderManagement.controler.dto;

import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.validation.password.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record UserCreateDto(
    @NotBlank(message = "Username is required.")
    String username,
    @PasswordValid
    String password,
    @NotBlank(message = "Birth date is required.")
    LocalDate birthDate) {

  public User toEntity() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setBirthDate(birthDate);
    return user;
  }
}
