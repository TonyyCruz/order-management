package com.anthony.orderManagement.controler.dto;

import com.anthony.orderManagement.validation.password.PasswordValid;

public record PasswordUpdateDto(
    String currentPassword,
    @PasswordValid
    String newPassword) {

}
