package com.anthony.blacksmithOnlineStore.controler.dto.blacksmith;

import com.anthony.blacksmithOnlineStore.entity.Blacksmith;
import java.math.BigDecimal;

public record BlacksmithResponseDto(Long id, String name, String description, BigDecimal rating) {

  public static BlacksmithResponseDto fromEntity(Blacksmith blacksmith) {
    return new BlacksmithResponseDto(
        blacksmith.getId(),
        blacksmith.getName(),
        blacksmith.getDescription(),
        blacksmith.getRatingAverage()
    );
  }
}
