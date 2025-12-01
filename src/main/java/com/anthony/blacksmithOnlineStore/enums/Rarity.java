package com.anthony.blacksmithOnlineStore.enums;

import lombok.Getter;

@Getter
public enum Rarity {
  COMMON("Common"),
  UNCOMMON("Uncommon"),
  RARE("Rare"),
  EPIC("Epic"),
  LEGENDARY("Legendary"),
  MYTHIC("Mythic"),
  DIVINE("Divine"),
  TRANSCENDENT("Transcendent");

  public final String level;

  Rarity(String level) {
    this.level = level;
  }
}
