package com.anthony.orderManagement.helper.mocks;

import com.anthony.orderManagement.controler.dto.user.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.user.UserCreateDto;
import com.anthony.orderManagement.controler.dto.user.UserUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.security.Role;
import java.time.LocalDate;
import java.util.UUID;

public class MockUser {

  public static User user() {
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setUsername("user_one");
    user.setPassword("123456");
    user.setRole(Role.CUSTOMER);
    user.setBirthDate(LocalDate.of(1995, 5, 15));
    return user;
  }

  public static User admin() {
    User admin = new User();
    admin.setId(UUID.randomUUID());
    admin.setUsername("super_admin");
    admin.setPassword("loginAdmin");
    admin.setRole(Role.ADMIN);
    admin.setBirthDate(LocalDate.of(1988, 1, 10));
    return admin;
  }

  public static User clone(User user) {
    User clone = new User();
    clone.setId(user.getId());
    clone.setUsername(user.getUsername());
    clone.setPassword(user.getPassword());
    clone.setBirthDate(user.getBirthDate());
    clone.setRole(user.getRole());
    return user;
  }

  // ========== DTOs ==========

  public static UserCreateDto userCreateDto() {
    return new UserCreateDto("user_one", "123456", LocalDate.of(1995, 5, 15));
  }

  public static UserUpdateDto userUpdateDto() {
    return new UserUpdateDto("user_two", LocalDate.of(1996, 6, 20));
  }

  public static PasswordUpdateDto passwordUpdateDto() {
    return new PasswordUpdateDto("123456", "newPassword");
  }

  public static PasswordUpdateDto invalidPasswordUpdateDto() {
    return new PasswordUpdateDto("wrongPassword", "newPassword");
  }
}
