package com.anthony.blacksmithOnlineStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Long productId;
  @Column(nullable = false)
  private String productName;
  @Column(nullable = false)
  private BigDecimal unitPrice;
  @Column(nullable = false)
  private Integer quantity;
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;
  @Column(nullable = false)
  private boolean reviewed = false;

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
        ", productId=" + productId +
        ", productName='" + productName + '\'' +
        ", unitPrice=" + unitPrice +
        ", quantity=" + quantity +
        ", order=" + order.getId() +
        '}';
  }
}
