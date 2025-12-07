package com.anthony.blacksmithOnlineStore.controler;

import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithResponse;
import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithRequestDto;
import com.anthony.blacksmithOnlineStore.entity.Blacksmith;
import com.anthony.blacksmithOnlineStore.service.BlacksmithService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blacksmiths")
public class BlacksmithController {
  private final BlacksmithService blacksmithService;

  @PostMapping("/blacksmith")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BlacksmithResponse> createBlacksmith(@RequestBody BlacksmithRequestDto dto) {
    Blacksmith createdBlacksmith = blacksmithService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BlacksmithResponse.fromEntity(createdBlacksmith));
  }

  @PutMapping("/blacksmiths/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BlacksmithResponse> updateBlacksmith(@RequestBody BlacksmithRequestDto dto,
      @PathVariable Long id) {
    Blacksmith createdBlacksmith = blacksmithService.update(id, dto);
    return ResponseEntity.ok(BlacksmithResponse.fromEntity(createdBlacksmith));
  }

  @GetMapping("/blacksmiths")
  public ResponseEntity<List<BlacksmithResponse>> findAll() {
    List<Blacksmith> blacksmiths = blacksmithService.findAll();
    return ResponseEntity.ok(blacksmiths.stream().map(BlacksmithResponse::fromEntity).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BlacksmithResponse> findById(@PathVariable Long id) {
    Blacksmith blacksmith = blacksmithService.findById(id);
    return ResponseEntity.ok(BlacksmithResponse.fromEntity(blacksmith));
  }

}
