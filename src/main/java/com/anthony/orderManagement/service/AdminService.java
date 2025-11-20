package com.anthony.orderManagement.service;

import com.anthony.orderManagement.controler.dto.RoleUpdateDto;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.exceptions.ForbiddenOperationException;
import com.anthony.orderManagement.exceptions.UserNotFoundException;
import com.anthony.orderManagement.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
  private final UserRepository userRepository;

  public User getById(UUID id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  public void updateRole(UUID id, RoleUpdateDto roleUpdateDto, Authentication auth) {
    UUID currentUserId = (UUID) auth.getDetails();
    if (currentUserId.equals(id)) {
      throw new ForbiddenOperationException("You cannot change your own role.");
    }
    User user = getById(id);
    user.setRole(roleUpdateDto.role());
    userRepository.save(user);
  }
}
