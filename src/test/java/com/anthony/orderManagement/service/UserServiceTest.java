package com.anthony.orderManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.anthony.orderManagement.controler.dto.user.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.user.UserCreateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.exceptions.InvalidCredentialsException;
import com.anthony.orderManagement.helper.mocks.MockUser;
import com.anthony.orderManagement.repository.UserRepository;
import com.anthony.orderManagement.security.Role;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private Authentication auth;

  @InjectMocks private UserService userService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void create_shouldEncodePasswordAndSaveUser_whenUsernameIsAvailable() {
    UserCreateDto dto = MockUser.userCreateDto();
    User user = MockUser.user();
    user.setRole(Role.CUSTOMER);
    when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
    when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded");
    when(userRepository.save(any(User.class))).thenReturn(user);
    User created = userService.create(dto);
    assertEquals(Role.CUSTOMER, created.getRole());
    verify(passwordEncoder).encode(user.getPassword());
    verify(userRepository, times(1)).save(any(User.class));
    verify(userRepository, times(1)).existsByUsername(user.getUsername());
  }

  @Test
  void updatePassword_shouldThrowInvalidCredentials_whenCurrentPasswordIsWrong() {
    User user = MockUser.user();
    user.setPassword("encoded");
    when(auth.getName()).thenReturn("user");
    when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("wrong", "encoded"))
        .thenReturn(false);
    PasswordUpdateDto dto = new PasswordUpdateDto("wrong", "newPass");
    assertThrows(InvalidCredentialsException.class,
        () -> userService.updatePassword(dto, auth));
    verify(userRepository, times(1)).findByUsername("user");
  }
}
