package com.anthony.orderManagement.service;

import com.anthony.orderManagement.controler.dto.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.UserCreateDto;
import com.anthony.orderManagement.controler.dto.UserUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.enums.Role;
import com.anthony.orderManagement.exceptions.InvalidCredentialsException;
import com.anthony.orderManagement.exceptions.UserNotFoundException;
import com.anthony.orderManagement.exceptions.UsernameAlreadyExistsException;
import com.anthony.orderManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User create(UserCreateDto createDto) {
    if (usernameExists(createDto.username())) throw new UsernameAlreadyExistsException();
    User user = createDto.toEntity();
    user.setRole(Role.CUSTOMER);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Transactional
  public User updateUser(UserUpdateDto updateDto, Authentication auth) {
    User user = getUserFromAuth(auth);
    boolean isUsernameChanged = !user.getUsername().equals(updateDto.username());
    if (isUsernameChanged && usernameExists(updateDto.username())) {
      throw new UsernameAlreadyExistsException();
    }
    user.setUsername(updateDto.username());
    user.setBirthDate(updateDto.birthDate());
    return userRepository.save(user);
  }

  @Transactional
  public User updatePassword(PasswordUpdateDto passwordUpdateDto, Authentication auth) {
    User user = getUserFromAuth(auth);
    if (!passwordEncoder.matches(passwordUpdateDto.currentPassword(), user.getPassword())) {
      throw new InvalidCredentialsException();
    }
    user.setPassword(passwordEncoder.encode(passwordUpdateDto.newPassword()));
    return userRepository.save(user);
  }

  private boolean usernameExists(String username) {
    return userRepository.existsByUsername(username);
  }

  private User getUserFromAuth(Authentication auth) {
    if (auth == null || !auth.isAuthenticated()) {
      throw new InvalidCredentialsException();
    }
    return userRepository.findByUsername(auth.getName())
        .orElseThrow(UserNotFoundException::new);
  }
}
