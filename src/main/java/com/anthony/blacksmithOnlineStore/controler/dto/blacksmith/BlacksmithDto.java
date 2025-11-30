package com.anthony.blacksmithOnlineStore.controler.dto;

import com.anthony.blacksmithOnlineStore.entity.Blacksmith;

public record BlacksmithDto(String name, String description, Double rating) {

  public Blacksmith toEntity() {
    Blacksmith blacksmith = new Blacksmith();
    blacksmith.setName(this.name);
    blacksmith.setDescription(this.description);
    return blacksmith;
  }

  public static BlacksmithDto fromEntity(Blacksmith blacksmith) {
    return new BlacksmithDto(
        blacksmith.getName(),
        blacksmith.getDescription(),
        blacksmith.getRating()
    );
  }
}
