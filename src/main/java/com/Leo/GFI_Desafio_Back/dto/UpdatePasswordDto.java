package com.Leo.GFI_Desafio_Back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(
        @NotBlank
        @Size(min = 6, message = "Password must have at least 6 characters")
        String password
) {}
