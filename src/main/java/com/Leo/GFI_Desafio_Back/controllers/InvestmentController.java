package com.Leo.GFI_Desafio_Back.controllers;

import com.Leo.GFI_Desafio_Back.dto.GenericResponseDto;
import com.Leo.GFI_Desafio_Back.dto.InvestmentRecordDto;
import com.Leo.GFI_Desafio_Back.dto.InvestmentResponseRecordDto;
import com.Leo.GFI_Desafio_Back.dto.InvestmentUpdateRecordDto;
import com.Leo.GFI_Desafio_Back.models.InvestmentModel;
import com.Leo.GFI_Desafio_Back.service.InvestmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/investments")
public class InvestmentController {
    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvestment(@PathVariable UUID id){
        Optional<InvestmentModel> investment = investmentService.getInvestment(id);
        if (investment.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(
                new InvestmentResponseRecordDto(
                        investment.get().getId(), investment.get().getName(), investment.get().getType(), investment.get().getValue(), investment.get().getStartDate(), investment.get().getUser().getId()
                    ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponseDto<String>("Investment not found", "NOT_FOUND"));
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<InvestmentResponseRecordDto> createInvestment(@RequestBody InvestmentRecordDto investmentRecordDto) {
        InvestmentModel investment = investmentService.createInvestment(investmentRecordDto);
        InvestmentResponseRecordDto investmentResponseRecordDto = new InvestmentResponseRecordDto(
                investment.getId(), investment.getName(), investment.getType(), investment.getValue(), investment.getStartDate(), investment.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(investmentResponseRecordDto);
    }

    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvestment(@PathVariable UUID id, @RequestBody InvestmentUpdateRecordDto investmentUpdateRecordDto) {
        Optional<InvestmentModel> investment = investmentService.updateInvestment(id, investmentUpdateRecordDto);
        if (investment.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(new InvestmentResponseRecordDto(
                investment.get().getId(), investment.get().getName(), investment.get().getType(), investment.get().getValue(),
                investment.get().getStartDate(), investment.get().getUser().getId()
        ));
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<String>("Investment not found or updated", "NOT_FOUND"));
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponseDto<String>> deleteInvestment(@PathVariable UUID id) {
        boolean del = investmentService.deleteInvestment(id).isPresent();
        if (del) return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<String>("Investment deleted successfully", "OK"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponseDto<String>("Investment not found or deleted", "NOT_FOUND"));
    }
}
