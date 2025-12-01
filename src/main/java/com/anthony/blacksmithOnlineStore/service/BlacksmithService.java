package com.anthony.blacksmithOnlineStore.service;

import com.anthony.blacksmithOnlineStore.controler.dto.blacksmith.BlacksmithRequestDto;
import com.anthony.blacksmithOnlineStore.entity.Blacksmith;
import com.anthony.blacksmithOnlineStore.exceptions.BlacksmithNotFoundException;
import com.anthony.blacksmithOnlineStore.repository.BlacksmithRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacksmithService {
  private final BlacksmithRepository blacksmithRepository;

  public Blacksmith findById(Long id) {
    return blacksmithRepository.findById(id).orElseThrow(BlacksmithNotFoundException::new);
  }

  @Transactional
  public Blacksmith create(BlacksmithRequestDto blacksmithDto) {
    return blacksmithRepository.save(blacksmithDto.toEntity());
  }

  @Transactional
  public Blacksmith update(Long id, BlacksmithRequestDto blacksmithDto) {
    Blacksmith blacksmith = findById(id);
    blacksmith.setName(blacksmithDto.name());
    blacksmith.setDescription(blacksmith.getDescription());
    return blacksmithRepository.save(blacksmith);
  }

  public List<Blacksmith> findAll() {
    return blacksmithRepository.findAll();
  }
}
