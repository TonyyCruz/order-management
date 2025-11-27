package com.anthony.orderManagement.controler.dto.admin;

import com.anthony.orderManagement.security.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDto(
    @NotNull Role role
) {}
