package com.anthony.orderManagement.controler;

import com.anthony.orderManagement.controler.dto.user.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.user.UserDto;
import com.anthony.orderManagement.controler.dto.user.UserUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @PutMapping("/me")
  public ResponseEntity<UserDto> updateUser(
      @RequestBody @Valid UserUpdateDto updateDto,
      Authentication auth) {
    User updatedUser = userService.updateUser(updateDto, auth);
    return ResponseEntity.ok(UserDto.fromEntity(updatedUser));
  }

  @PutMapping("/me/password")
  public ResponseEntity<UserDto> updateUserPassword(
      @RequestBody @Valid PasswordUpdateDto passwordUpdateDto,
      Authentication auth) {
    User updatedUser = userService.updatePassword(passwordUpdateDto, auth);
    return ResponseEntity.ok(UserDto.fromEntity(updatedUser));
  }
}
