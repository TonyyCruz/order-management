package com.anthony.blacksmithOnlineStore.controler.dto.Order;

import com.anthony.blacksmithOnlineStore.controler.dto.OrderItem.OderItemRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequestDto(
    @NotNull
    @NotEmpty
    List<OderItemRequestDto> items) {
}
