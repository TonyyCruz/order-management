package com.anthony.blacksmithOnlineStore.controler.dto.blacksmith;

import com.anthony.blacksmithOnlineStore.entity.Blacksmith;

public record BlacksmithDto(Long id, String name, String description, Double rating) {

  public static BlacksmithDto fromEntity(Blacksmith blacksmith) {
    return new BlacksmithDto(
        blacksmith.getId(),
        blacksmith.getName(),
        blacksmith.getDescription(),
        blacksmith.getRating()
    );
  }
}
