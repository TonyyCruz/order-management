package com.anthony.orderManagement.controler.dto;

import com.anthony.orderManagement.security.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDto(
    @NotNull Role role
) {}
