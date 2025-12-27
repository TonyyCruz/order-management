package com.anthony.blacksmithOnlineStore.controler.dto.Order;

import com.anthony.blacksmithOnlineStore.controler.dto.OrderItem.OrderItemRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequestDto(
    @NotNull
    @NotEmpty
    List<OrderItemRequestDto> items) {
}
