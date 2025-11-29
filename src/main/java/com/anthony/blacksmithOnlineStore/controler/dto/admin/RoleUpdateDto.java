package com.anthony.blacksmithOnlineStore.controler.dto.admin;

import com.anthony.blacksmithOnlineStore.security.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateDto(
    @NotNull Role role
) {}
