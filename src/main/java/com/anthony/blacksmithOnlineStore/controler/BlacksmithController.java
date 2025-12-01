package com.anthony.blacksmithOnlineStore.controler;

import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithDto;
import com.anthony.blacksmithOnlineStore.entity.Blacksmith;
import com.anthony.blacksmithOnlineStore.service.BlacksmithService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blacksmiths")
public class BlacksmithController {
  private final BlacksmithService blacksmithService;

  @GetMapping("/blacksmiths")
  public ResponseEntity<List<BlacksmithDto>> findAll() {
    List<Blacksmith> blacksmiths = blacksmithService.findAll();
    return ResponseEntity.ok(blacksmiths.stream().map(BlacksmithDto::fromEntity).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BlacksmithDto> findById(@PathVariable Long id) {
    Blacksmith blacksmith = blacksmithService.findById(id);
    return ResponseEntity.ok(BlacksmithDto.fromEntity(blacksmith));
  }

}
