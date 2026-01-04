package com.anthony.blacksmithOnlineStore.controler.dto.item;

import com.anthony.blacksmithOnlineStore.entity.Item;
import com.anthony.blacksmithOnlineStore.enums.Material;
import com.anthony.blacksmithOnlineStore.enums.Rarity;
import com.anthony.blacksmithOnlineStore.enums.Type;
import java.math.BigDecimal;

public record ItemRequestDto(
    String name,
    Material material,
    Integer baseDamage,
    Integer baseDefense,
    BigDecimal price,
    String description,
    Float weight,
    Integer stock,
    Type type,
    Rarity rarity,
    Long blacksmithId,
    boolean active
) {

  public static Item toEntity(ItemRequestDto dto) {
    return Item.builder()
        .material(dto.material())
        .baseDamage(dto.baseDamage())
        .baseDefense(dto.baseDefense())
        .name(dto.name())
        .basePrice(dto.price())
        .description(dto.description())
        .weight(dto.weight())
        .stock(dto.stock())
        .type(dto.type())
        .rarity(dto.rarity())
        .active(dto.active())
        .build();
  }
}
