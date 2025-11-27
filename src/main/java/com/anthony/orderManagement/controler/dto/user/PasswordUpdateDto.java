package com.anthony.orderManagement.controler.dto.user;

import com.anthony.orderManagement.validation.user.PasswordValid;

public record PasswordUpdateDto(
    String currentPassword,
    @PasswordValid
    String newPassword) {

}
