package com.anthony.blacksmithOnlineStore.service.util;

import com.anthony.blacksmithOnlineStore.controler.dto.OrderItem.OrderItemRequestDto;
import com.anthony.blacksmithOnlineStore.entity.Item;
import com.anthony.blacksmithOnlineStore.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemFactory {

  public OrderItem create(OrderItemRequestDto dto, Item item) {
    OrderItem orderItem = new OrderItem();
    orderItem.setItemId(dto.itemId());
    orderItem.setItemName(item.getName());
    orderItem.setBasePriceAtPurchase(item.getBasePrice());
    orderItem.setPriceApplied(item.getFinalPrice());
    orderItem.setQuantity(dto.quantity());
    orderItem.setBlacksmithId(item.getCraftedBy().getId());
    orderItem.calculateTotal();
    return orderItem;
  }
}
