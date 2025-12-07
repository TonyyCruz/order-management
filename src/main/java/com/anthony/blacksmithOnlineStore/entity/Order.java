package com.anthony.blacksmithOnlineStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jdk.jshell.Snippet.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @CreationTimestamp
  private LocalDateTime createdAt;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;
  @Setter(AccessLevel.NONE)
  @OneToMany(mappedBy = "order")
  private final List<OrderItem> orderItems = new ArrayList<>();
  @Setter(AccessLevel.NONE)
  private BigDecimal total = BigDecimal.ZERO;

  public void addItem(OrderItem item) {
    orderItems.add(item);
    total = total.add(item.getUnitPrice());
  }

  public void removeItem(OrderItem item) {
    if (orderItems.remove(item)) {
      total = total.subtract(item.getUnitPrice());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", user=" + user.getId() +
        ", createdAt=" + createdAt +
        ", status=" + status +
        ", orderItems=" + orderItems +
        ", total=" + total +
        '}';
  }
}
