package com.anthony.orderManagement.controler.dto.user;

import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.validation.user.PasswordValid;
import com.anthony.orderManagement.validation.user.ValidAge;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserCreateDto(
    @NotBlank(message = "Username is required.")
    String username,
    @PasswordValid
    String password,
    @NotNull(message = "Birth date is required.")
    @ValidAge(min = 18, message = "User must be at least 18 years old.")
    LocalDate birthDate) {

  public User toEntity() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setBirthDate(birthDate);
    return user;
  }
}
