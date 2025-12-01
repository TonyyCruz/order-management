package com.anthony.blacksmithOnlineStore.controler;

import com.anthony.blacksmithOnlineStore.controler.dto.admin.RoleUpdateDto;
import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithDto;
import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithRequestDto;
import com.anthony.blacksmithOnlineStore.controler.dto.user.UserDto;
import com.anthony.blacksmithOnlineStore.entity.Blacksmith;
import com.anthony.blacksmithOnlineStore.service.AdminService;
import com.anthony.blacksmithOnlineStore.service.BlacksmithService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
  private final AdminService adminService;
  private final BlacksmithService blacksmithService;

  @PatchMapping("/users/{id}/role")
  public ResponseEntity<Void> updateUserRole(@PathVariable UUID id,
      @RequestBody @Valid RoleUpdateDto roleUpdateDto,
      Authentication auth) {
    adminService.updateRole(id, roleUpdateDto, auth);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/users")
  public ResponseEntity<UserDto> findByUsername(@RequestParam String username) {
    return ResponseEntity.ok(UserDto.fromEntity(adminService.findByUsername(username)));
  }

  @PostMapping("/blacksmith")
  public ResponseEntity<BlacksmithDto> createBlacksmith(@RequestBody BlacksmithRequestDto dto) {
    Blacksmith createdBlacksmith = blacksmithService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BlacksmithDto.fromEntity(createdBlacksmith));
  }

  @PutMapping("/blacksmiths/{id}")
  public ResponseEntity<BlacksmithDto> updateBlacksmith(@RequestBody BlacksmithRequestDto dto,
      @PathVariable Long id) {
    Blacksmith createdBlacksmith = blacksmithService.update(id, dto);
    return ResponseEntity.ok(BlacksmithDto.fromEntity(createdBlacksmith));
  }

}
