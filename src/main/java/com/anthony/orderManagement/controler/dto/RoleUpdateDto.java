package com.anthony.orderManagement.controler.dto;

import com.anthony.orderManagement.enums.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDto(
    @NotNull Role role
) {}
