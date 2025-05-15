package com.Leo.GFI_Desafio_Back.repositories;

import com.Leo.GFI_Desafio_Back.models.InvestmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<InvestmentModel, UUID> {
}