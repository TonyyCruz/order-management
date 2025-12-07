package com.anthony.blacksmithOnlineStore.controler.dto.blacksmith;

import com.anthony.blacksmithOnlineStore.entity.Blacksmith;

public record BlacksmithResponse(Long id, String name, String description, Double rating) {

  public static BlacksmithResponse fromEntity(Blacksmith blacksmith) {
    return new BlacksmithResponse(
        blacksmith.getId(),
        blacksmith.getName(),
        blacksmith.getDescription(),
        blacksmith.getRatingAverage()
    );
  }
}
