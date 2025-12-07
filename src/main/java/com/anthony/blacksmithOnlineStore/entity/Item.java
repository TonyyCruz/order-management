package com.anthony.blacksmithOnlineStore.entity;

import com.anthony.blacksmithOnlineStore.enums.Material;
import com.anthony.blacksmithOnlineStore.enums.Rarity;
import com.anthony.blacksmithOnlineStore.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  private Material material;
  private Integer baseDamage = 0;
  private Integer baseDefense = 0;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private BigDecimal price;
  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;
  @Column(nullable = false)
  private Float weight;
  @Column(nullable = false)
  private Integer stock;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Type type;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Rarity rarity;
  @ManyToOne(optional = false)
  @JoinColumn(name = "blacksmith_id")
  private Blacksmith craftedBy;
  @Setter(AccessLevel.NONE)
  private Double ratingAverage = 0.0;
  @Setter(AccessLevel.NONE)
  @Column(nullable = false)
  private Integer ratingCount = 0;
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;
  @Column(nullable = false)
  private boolean active = true;

  public void addRating(Rating newRating) {
    ratingAverage = ((ratingAverage * ratingCount) + newRating.getRating()) / (ratingCount + 1);
    ratingCount += 1;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return Objects.equals(id, item.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

}
