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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser(Authentication auth) {
    User user = userService.getUserFromAuth(auth);
    return ResponseEntity.ok(UserDto.fromEntity(user));
  }

  @PutMapping("/me")
  public ResponseEntity<UserDto> updateCurrentUser(
      @RequestBody @Valid UserUpdateDto updateDto,
      Authentication auth) {
    User updatedUser = userService.updateUser(updateDto, auth);
    return ResponseEntity.ok(UserDto.fromEntity(updatedUser));
  }

  @PutMapping("/me/password")
  public ResponseEntity<UserDto> updateCurrentUserPassword(
      @RequestBody @Valid PasswordUpdateDto passwordUpdateDto,
      Authentication auth) {
    User updatedUser = userService.updatePassword(passwordUpdateDto, auth);
    return ResponseEntity.ok(UserDto.fromEntity(updatedUser));
  }

  @DeleteMapping("/me")
  public ResponseEntity<Void> deleteCurrentUser(Authentication auth) {
    userService.deleteUserFromAuth(auth);
    return ResponseEntity.noContent().build();
  }
}
