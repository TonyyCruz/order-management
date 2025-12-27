package com.anthony.blacksmithOnlineStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "ratings")
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  @Setter(AccessLevel.NONE)
  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "order_item_id", unique = true)
  private OrderItem orderItem;
  @Column(nullable = false)
  private Integer ratingValue;
  @Column(columnDefinition = "TEXT")
  private String review;
  @CreationTimestamp
  private LocalDate date;

  public void setOrderItem(OrderItem orderItem) {
    if (!this.orderItem.equals(orderItem)) {
      this.orderItem = orderItem;
      orderItem.setRating(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Rating rating = (Rating) o;
    return Objects.equals(id, rating.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Rating{" +
        "id=" + id +
        ", user=" + (user == null ? null : user.getId()) +
        ", item=" + orderItem +
        ", rating=" + ratingValue +
        ", comment='" + review + '\'' +
        ", date=" + date +
        '}';
  }
}
