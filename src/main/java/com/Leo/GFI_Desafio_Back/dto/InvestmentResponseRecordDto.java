package com.Leo.GFI_Desafio_Back.dto;

import com.Leo.GFI_Desafio_Back.models.InvestmentModel;
import com.Leo.GFI_Desafio_Back.models.InvestmentTypeEnum;
import com.Leo.GFI_Desafio_Back.models.UserModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

public record InvestmentResponseRecordDto(
        UUID id,
        String name,
        InvestmentTypeEnum type,
        double value,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date startDate,
        UUID userId
) {
        public static InvestmentResponseRecordDto fromModel(InvestmentModel model) {
                return new InvestmentResponseRecordDto(
                        model.getId(),
                        model.getName(),
                        model.getType(),
                        model.getValue(),
                        model.getStartDate(),
                        model.getUser().getId()
                );
        }
}
