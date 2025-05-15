package com.Leo.GFI_Desafio_Back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(
        @Email
        String email,
        @NotBlank
        String password
) {}