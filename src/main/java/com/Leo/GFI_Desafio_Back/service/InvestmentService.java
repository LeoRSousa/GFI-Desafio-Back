package com.Leo.GFI_Desafio_Back.service;

import com.Leo.GFI_Desafio_Back.dto.InvestmentRecordDto;
import com.Leo.GFI_Desafio_Back.dto.InvestmentUpdateRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserRecordDto;
import com.Leo.GFI_Desafio_Back.models.InvestmentModel;
import com.Leo.GFI_Desafio_Back.models.UserModel;
import com.Leo.GFI_Desafio_Back.repositories.InvestmentRepository;
import com.Leo.GFI_Desafio_Back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    public InvestmentService(InvestmentRepository investmentRepository, UserRepository userRepository) {
        this.investmentRepository = investmentRepository;
        this.userRepository = userRepository;
    }

    //CREATE
    @Transactional
    public InvestmentModel createInvestment(InvestmentRecordDto investmentRecordDto) {
        InvestmentModel investment = new InvestmentModel();
        investment.setName(investmentRecordDto.name());
        investment.setType(investmentRecordDto.type());
        investment.setValue(investmentRecordDto.value());
        investment.setStartDate(investmentRecordDto.startDate());
        investment.setUser(userRepository.findById(investmentRecordDto.userId()).get());

        return investmentRepository.save(investment);
    }

    //READ (by ID)
    public Optional<InvestmentModel> getInvestment(UUID id) {
        return investmentRepository.findById(id);
    }

    //UPDATE
    //Não é possível mudar o usuário associado ao investimento
    @Transactional
    public Optional<InvestmentModel> updateInvestment(UUID id, InvestmentUpdateRecordDto updatedInvestment) {
        return  investmentRepository.findById(id).map(existingInvestment -> {
           existingInvestment.setName(updatedInvestment.name());
           existingInvestment.setType(updatedInvestment.type());
           existingInvestment.setValue(updatedInvestment.value());
           existingInvestment.setStartDate(updatedInvestment.startDate());
           return investmentRepository.save(existingInvestment);
        });
    }

    //DELETE
    public Optional<InvestmentModel> deleteInvestment(UUID id) {
        Optional<InvestmentModel> investment = investmentRepository.findById(id);
        if(investment.isPresent()) investmentRepository.deleteById(id);
        return investment;
    }
}
