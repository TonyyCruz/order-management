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
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
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
@Table(name = "order_items")
public class OrderItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Long itemId;
  @Column(nullable = false)
  private String itemName;
  @Column(nullable = false)
  private BigDecimal basePriceAtPurchase;
  @Column(nullable = false)
  private BigDecimal priceApplied;
  @Column(nullable = false)
  private Integer quantity;
  @Column(nullable = false)
  private BigDecimal totalPrice;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;
  @Setter(AccessLevel.NONE)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rating_id", unique = true)
  private Rating rating;
  @Setter(AccessLevel.NONE)
  private Integer ratingValue;
  @Column(nullable = false, updatable = false)
  private UUID userId;
  @Column(nullable = false, updatable = false)
  private Long blacksmithId;
  @Column(nullable = false)
  private boolean reviewed = false;

  public void setPriceApplied(BigDecimal priceApplied) {
    checkIfFinalized();
    this.priceApplied = priceApplied;
  }

  public void setQuantity(Integer quantity) {
    checkIfFinalized();
    this.quantity = quantity;
  }

  public void calculateTotal() {
    if (priceApplied != null && quantity != null) {
      this.totalPrice = priceApplied.multiply(BigDecimal.valueOf(quantity));
    }
  }

  public void setRating(Rating rating) {
    if (!this.rating.equals(rating)) {
      this.rating = rating;
      this.ratingValue = rating.getRatingValue();
      this.reviewed = true;
      rating.setOrderItem(this);
    }
  }

  private void checkIfFinalized() {
    if (order != null && order.getStatus().isFinalState()) {
      throw new IllegalStateException("Item não pode ser alterado após finalização.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(id, orderItem.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", productId=" + itemId +
        ", productName='" + itemName + '\'' +
        ", unitPrice=" + basePriceAtPurchase +
        ", quantity=" + quantity +
        ", totalPrice=" + totalPrice +
        ", ratingValue=" + ratingValue +
        ", order=" + (order == null ? null : order.getId()) +
        '}';
  }
}
