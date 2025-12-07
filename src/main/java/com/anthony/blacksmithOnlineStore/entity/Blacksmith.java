package com.anthony.blacksmithOnlineStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacksmiths")
public class Blacksmith {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;
  @Column(nullable = false)
  @OneToMany(mappedBy = "craftedBy")
  private final List<Item> craftedItems = new ArrayList<>();
  @Column(nullable = false)
  @Setter(AccessLevel.NONE)
  private Double ratingAverage = 0.0;
  @Column(nullable = false)
  @Setter(AccessLevel.NONE)
  private Integer ratingCount = 0;

  public void addRating(Rating newRating) {
    ratingAverage = ((ratingAverage * ratingCount) + newRating.getRating()) / (ratingCount + 1);
    ratingCount += 1;
  }

  public void addCraftedItem(Item item) {
    craftedItems.add(item);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Blacksmith that = (Blacksmith) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Blacksmith{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", ratingAverage=" + ratingAverage +
        ", ratingCount=" + ratingCount +
        '}';
  }
}
