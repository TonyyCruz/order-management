package com.anthony.blacksmithOnlineStore.controler.dto.OrderItem;

public record OderItemResponseDto(
    Long id,
    Long productId,
    String productName,
    String unitPrice,
    Integer quantity,
    String subtotal) {
}
