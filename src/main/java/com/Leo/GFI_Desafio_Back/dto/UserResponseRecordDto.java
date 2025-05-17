package com.Leo.GFI_Desafio_Back.dto;

import com.Leo.GFI_Desafio_Back.models.InvestmentModel;

import java.util.Set;
import java.util.UUID;

public record UserResponseRecordDto(
        UUID id,
        String email,
        Set<InvestmentResponseRecordDto> investments
) {}