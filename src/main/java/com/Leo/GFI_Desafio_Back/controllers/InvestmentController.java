package com.Leo.GFI_Desafio_Back.controllers;

import com.Leo.GFI_Desafio_Back.dto.InvestmentRecordDto;
import com.Leo.GFI_Desafio_Back.dto.InvestmentUpdateRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserRecordDto;
import com.Leo.GFI_Desafio_Back.models.InvestmentModel;
import com.Leo.GFI_Desafio_Back.models.UserModel;
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
        if (investment.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(investment);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investment not found.");
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<InvestmentModel> createInvestment(@RequestBody InvestmentRecordDto investmentRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(investmentService.createInvestment(investmentRecordDto));
    }

    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvestment(@PathVariable UUID id, @RequestBody InvestmentUpdateRecordDto investmentUpdateRecordDto) {
        Optional<InvestmentModel> investment = investmentService.updateInvestment(id, investmentUpdateRecordDto);
        if (investment.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(investment);
        return ResponseEntity.status(HttpStatus.OK).body("Investment not found or updated");
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInvestment(@PathVariable UUID id) {
        boolean del = investmentService.deleteInvestment(id).isPresent();
        if (del) return ResponseEntity.status(HttpStatus.OK).body("Investment deleted successfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investment not found or deleted");
    }
}
