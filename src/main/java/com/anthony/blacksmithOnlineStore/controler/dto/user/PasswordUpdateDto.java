package com.anthony.blacksmithOnlineStore.controler.dto.user;

import com.anthony.blacksmithOnlineStore.validation.user.PasswordValid;

public record PasswordUpdateDto(
    String currentPassword,
    @PasswordValid
    String newPassword) {

}
