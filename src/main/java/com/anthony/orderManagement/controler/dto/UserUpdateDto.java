package com.anthony.orderManagement.controler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record UserUpdateDto(
    @NotBlank String username,
    @Past LocalDate birthDate
) { }
