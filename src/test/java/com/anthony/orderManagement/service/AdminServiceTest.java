package com.anthony.orderManagement.service;

import com.anthony.orderManagement.controler.dto.RoleUpdateDto;
import com.anthony.orderManagement.exceptions.UnauthorizedOperationException;
import com.anthony.orderManagement.helper.mocks.MockUser;
import com.anthony.orderManagement.security.Role;
import com.anthony.orderManagement.exceptions.UserNotFoundException;
import com.anthony.orderManagement.entity.User;
import com.anthony.orderManagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
  @Mock private UserRepository userRepository;
  @Mock private Authentication auth;
  @InjectMocks private AdminService adminService;
  private User targetUser;
  private User adminUser;

  @BeforeEach
  void setup() {
    targetUser = MockUser.user();
    adminUser = MockUser.admin();
  }

  @Test
  void updateRole_ShouldUpdateUserRole_WhenValid() {
    UUID targetId = targetUser.getId();
    UUID adminId = adminUser.getId();
    RoleUpdateDto dto = new RoleUpdateDto(Role.ADMIN);
    when(userRepository.findById(targetId)).thenReturn(Optional.of(targetUser));
    when(auth.getDetails()).thenReturn(adminId);
    adminService.updateRole(targetId, dto, auth);
    assertEquals(Role.ADMIN, targetUser.getRole());
    verify(userRepository).save(targetUser);
  }

  @Test
  void updateRole_ShouldThrow_WhenUserNotFound() {
    UUID fakeId = UUID.randomUUID();
    when(userRepository.findById(fakeId)).thenReturn(Optional.empty());
    when(auth.getDetails()).thenReturn(adminUser.getId());
    RoleUpdateDto dto = new RoleUpdateDto(Role.ADMIN);
    assertThrows(UserNotFoundException.class, () -> adminService.updateRole(fakeId, dto, auth));
    verify(userRepository, never()).save(any());
  }

  @Test
  void updateRole_ShouldThrow_WhenAdminTriesToChangeOwnRole() {
    UUID adminId = adminUser.getId();
    RoleUpdateDto dto = new RoleUpdateDto(Role.CUSTOMER);
    when(auth.getDetails()).thenReturn(adminId);
    assertThrows(UnauthorizedOperationException.class,
        () -> adminService.updateRole(adminId, dto, auth));
    verify(userRepository, never()).save(any());
  }

  @Test
  void getById_ShouldReturnUser_WhenExists() {
    when(userRepository.findById(targetUser.getId())).thenReturn(Optional.of(targetUser));
    User found = adminService.getById(targetUser.getId());
    assertEquals(targetUser, found);
  }

  @Test
  void getById_ShouldThrow_WhenNotFound() {
    UUID fakeId = UUID.randomUUID();
    when(userRepository.findById(fakeId)).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class, () -> adminService.getById(fakeId));
  }
}
