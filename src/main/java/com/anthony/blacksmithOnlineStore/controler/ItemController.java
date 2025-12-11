package com.anthony.blacksmithOnlineStore.controler;

import com.anthony.blacksmithOnlineStore.controler.dto.item.ItemFilterDto;
import com.anthony.blacksmithOnlineStore.controler.dto.item.ItemRequestDto;
import com.anthony.blacksmithOnlineStore.controler.dto.item.ItemResponseDto;
import com.anthony.blacksmithOnlineStore.entity.Item;
import com.anthony.blacksmithOnlineStore.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
  private final ItemService itemService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ItemResponseDto> createItem(@RequestBody @Valid ItemRequestDto dto) {
    Item item = itemService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(ItemResponseDto.fromEntity(item));
  }

  @PutMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ItemResponseDto> updateItem(@RequestBody @Valid ItemRequestDto dto) {
    Item item = itemService.update(dto);
    return ResponseEntity.ok(ItemResponseDto.fromEntity(item));
  }

  @PatchMapping("/{id}/deactivate")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deactivateItem(@PathVariable Long id) {
    itemService.deactivateItem(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/activate")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> activateItem(@PathVariable Long id) {
    itemService.activateItem(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemResponseDto> getItemById(@PathVariable Long id) {
    Item item = itemService.findById(id);
    return ResponseEntity.ok(ItemResponseDto.fromEntity(item));
  }

  @GetMapping
  public ResponseEntity<Page<ItemResponseDto>> getAllItems(
      @PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.DESC)
      Pageable pageable) {
    Page<Item> items = itemService.findAll(pageable);
    return ResponseEntity.ok(items.map(ItemResponseDto::fromEntity));
  }

  @GetMapping("/blacksmith/{blacksmithId}")
  public ResponseEntity<Page<ItemResponseDto>> getItensByBlacksmithId(
      @PathVariable Long blacksmithId,
      @PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.DESC)
      Pageable pageable) {
    Page<Item> items = itemService.findByBlacksmithId(blacksmithId, pageable);
    return ResponseEntity.ok(items.map(ItemResponseDto::fromEntity));
  }

  @PostMapping("/filter")
  public ResponseEntity<Page<ItemResponseDto>> getFilteredItems(
      @RequestBody ItemFilterDto filter,
      @PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.DESC)
      Pageable pageable) {
    Page<Item> items = itemService.findFilteredItems(filter, pageable);
    return ResponseEntity.ok(items.map(ItemResponseDto::fromEntity));
  }
}
