package com.anthony.orderManagement.service;

import com.anthony.orderManagement.controler.dto.user.PasswordUpdateDto;
import com.anthony.orderManagement.controler.dto.user.UserCreateDto;
import com.anthony.orderManagement.controler.dto.user.UserUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.exceptions.InvalidCredentialsException;
import com.anthony.orderManagement.exceptions.UserNotFoundException;
import com.anthony.orderManagement.exceptions.UsernameAlreadyExistsException;
import com.anthony.orderManagement.repository.UserRepository;
import com.anthony.orderManagement.security.Role;
import jakarta.transaction.Transactional;
import java.util.UUID;
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

  public User updateUser(UserUpdateDto updateDto, Authentication auth) {
    boolean isUsernameChanged = !auth.getName().equals(updateDto.username());
    if (isUsernameChanged && usernameExists(updateDto.username())) {
      throw new UsernameAlreadyExistsException();
    }
    User user = userRepository.getReferenceById((UUID) auth.getDetails());
    user.setUsername(updateDto.username());
    user.setBirthDate(updateDto.birthDate());
    return userRepository.save(user); // Opcional com o @Transactional, mas melhora a testabilidade
  }

  public User updatePassword(PasswordUpdateDto passwordUpdateDto, Authentication auth) {
    User user = getUserFromAuth(auth);
    if (!passwordEncoder.matches(passwordUpdateDto.currentPassword(), user.getPassword())) {
      throw new InvalidCredentialsException();
    }
    user.setPassword(passwordEncoder.encode(passwordUpdateDto.newPassword()));
    return userRepository.save(user); // Opcional com o @Transactional, mas melhora a testabilidade
  }

  private boolean usernameExists(String username) {
    return userRepository.existsByUsername(username);
  }

  private User getUserFromAuth(Authentication auth) {
    return userRepository.findByUsername(auth.getName())
        .orElseThrow(UserNotFoundException::new);
  }
}
