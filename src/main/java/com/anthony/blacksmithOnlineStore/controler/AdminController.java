package com.anthony.blacksmithOnlineStore.controler;

import com.anthony.blacksmithOnlineStore.controler.dto.admin.RoleUpdateDto;
import com.anthony.blacksmithOnlineStore.service.AdminService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
  private final AdminService adminService;

  @PatchMapping("/users/{id}/role")
  public ResponseEntity<Void> updateUserRole(@PathVariable UUID id,
      @RequestBody @Valid RoleUpdateDto roleUpdateDto,
      Authentication auth) {
    adminService.updateRole(id, roleUpdateDto, auth);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
