package com.anthony.blacksmithOnlineStore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
  @Setter(AccessLevel.NONE)
  @OneToMany(mappedBy = "blacksmith", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Rating> ratings = new ArrayList<>();

  public Double getRating() {
    if (ratings.isEmpty()) return 0.0;
    Double sum = ratings.stream()
        .reduce(0.0, (acc, r) -> acc + r.getRating(), Double::sum);
    return sum / ratings.size();
  }

  @Override
  public String toString() {
    return "Blacksmith{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", rating=" + getRating() +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Blacksmith that = (Blacksmith) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
