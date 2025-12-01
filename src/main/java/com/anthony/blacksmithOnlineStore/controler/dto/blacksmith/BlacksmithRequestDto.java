package com.anthony.blacksmithOnlineStore.controler.dto.blacksmith;

import com.anthony.blacksmithOnlineStore.entity.Blacksmith;

public record BlacksmithRequestDto(String name, String description) {

  public Blacksmith toEntity() {
    Blacksmith blacksmith = new Blacksmith();
    blacksmith.setName(this.name);
    blacksmith.setDescription(this.description);
    return blacksmith;
  }
}
