package com.anthony.orderManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.anthony.orderManagement.controler.dto.user.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.user.UserCreateDto;
import com.anthony.orderManagement.controler.dto.user.UserUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.exceptions.InvalidCredentialsException;
import com.anthony.orderManagement.helper.mocks.MockUser;
import com.anthony.orderManagement.repository.UserRepository;
import com.anthony.orderManagement.security.Role;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private Authentication auth;
  @InjectMocks private UserService userService;

  @Test
  void create_shouldEncodePasswordAndSaveUser_whenUsernameIsAvailable() {
    UserCreateDto dto = MockUser.userCreateDto();
    User user = dto.toEntity();

    when(userRepository.existsByUsername(dto.username())).thenReturn(false);
    when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded");
    when(userRepository.save(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    User created = userService.create(dto);

    assertEquals(Role.CUSTOMER, created.getRole(), "The default role must be CUSTOMER");
    verify(passwordEncoder).encode(user.getPassword());
    assertEquals("encoded", created.getPassword(), "The password must be encoded");
    assertEquals(dto.username(), created.getUsername(), "The username must be maintained");
    assertEquals(dto.birthDate(), created.getBirthDate(), "The birth date must be maintained");
    verify(userRepository, times(1)).save(any(User.class));
    verify(userRepository, times(1)).existsByUsername(dto.username());
  }

  @Test
  void update_CanUpdateUsernameAndBirthdate_whenUsernameIsAvailable() {
    UserUpdateDto dto = MockUser.userUpdateDto();
    User user = MockUser.user();

    when(auth.getName()).thenReturn(user.getUsername());
    when(auth.getDetails()).thenReturn(user.getId());
    when(userRepository.existsByUsername(dto.username())).thenReturn(false);
    when(userRepository.getReferenceById(any(UUID.class))).thenReturn(user);
    when(userRepository.save(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    User updated = userService.updateUser(dto, auth);

    assertEquals(Role.CUSTOMER, updated.getRole(), "The Role should not be changed");
    assertEquals(user.getPassword(), updated.getPassword(), "The password should not be changed");
    assertEquals(dto.username(), updated.getUsername(), "The username must be updated");
    assertEquals(dto.birthDate(), updated.getBirthDate(), "The birth date must be updated");
    assertSame(user, updated, "Update should modify the same user instance");
    verify(userRepository, times(1)).save(any(User.class));
    verify(userRepository, times(1)).existsByUsername(dto.username());
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
