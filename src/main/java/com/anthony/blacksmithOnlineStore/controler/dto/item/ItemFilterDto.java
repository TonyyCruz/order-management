package com.anthony.blacksmithOnlineStore.controler.dto.item;

import com.anthony.blacksmithOnlineStore.enums.Material;
import com.anthony.blacksmithOnlineStore.enums.Rarity;
import com.anthony.blacksmithOnlineStore.enums.Type;
import java.math.BigDecimal;

public record ItemFilterDto(
    String name,
    Material material,
    Integer baseDamage,
    Integer baseDefense,
    BigDecimal highestPrice,
    BigDecimal lowestPrice,
    Float maxWeight,
    Type type,
    Rarity rarity,
    Long blacksmithId
) {

}
