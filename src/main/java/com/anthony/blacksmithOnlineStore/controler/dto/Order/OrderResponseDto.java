package com.anthony.blacksmithOnlineStore.controler.dto.Order;

import com.anthony.blacksmithOnlineStore.controler.dto.OrderItem.OderItemResponseDto;
import com.anthony.blacksmithOnlineStore.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
    Long id,
    UUID userId,
    LocalDateTime createdAt,
    OrderStatus status,
    List<OderItemResponseDto> items,
    BigDecimal total) {
}
