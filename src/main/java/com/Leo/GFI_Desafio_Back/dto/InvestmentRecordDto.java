package com.Leo.GFI_Desafio_Back.dto;

import com.Leo.GFI_Desafio_Back.models.InvestmentModel;
import com.Leo.GFI_Desafio_Back.models.InvestmentTypeEnum;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

public record InvestmentRecordDto(
        @NotBlank(message = "O nome do investimento não pode ser vazio.")
        @Min(value = 3, message = "Nome do investimento é muito curto")
        String name,

        @NotNull(message = "O tipo de investimento não pode ser nulo.")
        InvestmentTypeEnum type,

        @Positive(message = "Valor deve ser maior que 0.")
        double value,

        @NotNull(message = "A data de início não pode ser nula.")
        @PastOrPresent(message = "Data não pode estar no futuro.")
        @Temporal(TemporalType.DATE)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date startDate,

        @NotNull(message = "O ID do usuário não pode ser nulo.")
        UUID userId
) {}