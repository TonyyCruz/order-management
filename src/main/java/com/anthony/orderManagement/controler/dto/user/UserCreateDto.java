package com.anthony.orderManagement.controler.dto.user;

import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.validation.password.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record UserCreateDto(
    @NotBlank(message = "Username is required.")
    String username,
    @PasswordValid
    String password,
    @NotNull(message = "Birth date is required.")
    @Past(message = "Birth date must be in the past.")
    LocalDate birthDate) {

  public User toEntity() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setBirthDate(birthDate);
    return user;
  }
}
