package com.anthony.blacksmithOnlineStore.service;

import com.anthony.blacksmithOnlineStore.controler.dto.item.ItemFilterDto;
import com.anthony.blacksmithOnlineStore.controler.dto.item.ItemRequestDto;
import com.anthony.blacksmithOnlineStore.entity.Item;
import com.anthony.blacksmithOnlineStore.exceptions.ItemNotFoundException;
import com.anthony.blacksmithOnlineStore.repository.ItemRepository;
import com.anthony.blacksmithOnlineStore.repository.specification.ItemSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {
  private final ItemRepository itemRepository;
  private final BlacksmithService blacksmithService;

  @Transactional
  public Item create(ItemRequestDto dto) {
    Item item = ItemRequestDto.toEntity(dto);
    item.setCraftedBy(blacksmithService.getReferenceById(dto.blacksmithId()));
    return itemRepository.save(item);
  }

  @Transactional
  public Item update(ItemRequestDto dto) {
    Item item = ItemRequestDto.toEntity(dto);
    item.setCraftedBy(blacksmithService.getReferenceById(dto.blacksmithId()));
    return itemRepository.save(item);
  }

  public Item findById(Long id) {
    return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
  }

  public Page<Item> findAll(Pageable pageable) {
    return itemRepository.findAll(pageable);
  }

  public void deactivateItem(Long id) {
    Item item = getReferenceById(id);
    item.setActive(false);
    itemRepository.save(item);
  }

  public void activateItem(Long id) {
    Item item = getReferenceById(id);
    item.setActive(true);
    itemRepository.save(item);
  }

  public Item getReferenceById(Long id) {
    if (!itemRepository.existsById(id)) throw new ItemNotFoundException(id);
    return itemRepository.getReferenceById(id);
  }

  public Page<Item> findByBlacksmithId(Long blacksmithId, Pageable pageable) {
    return itemRepository.findByCraftedById(blacksmithId, pageable);
  }

  public Page<Item> findFilteredItems(ItemFilterDto filter, Pageable pageable) {
    Specification<Item> specs = ItemSpecifications.withFilters(filter);
    return itemRepository.findAll(specs, pageable);
  }
}
