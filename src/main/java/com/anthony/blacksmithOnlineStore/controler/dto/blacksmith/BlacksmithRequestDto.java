package com.anthony.blacksmithOnlineStore.controler.dto.blacksmith;

import com.anthony.blacksmithOnlineStore.entity.Blacksmith;

public record BlacksmithRequestDto(String name, String description) {

  public static Blacksmith toEntity(BlacksmithRequestDto dto) {
    Blacksmith blacksmith = new Blacksmith();
    blacksmith.setName(dto.name);
    blacksmith.setDescription(dto.description);
    return blacksmith;
  }
}
