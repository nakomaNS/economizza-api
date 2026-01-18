package com.nakomans.economizza.controller;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nakomans.economizza.dto.TransactionRequest;
import com.nakomans.economizza.enums.TransactionType;
import com.nakomans.economizza.model.Transactions;
import com.nakomans.economizza.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/income")
    public ResponseEntity<Transactions> registerIncome(@RequestBody TransactionRequest request) {
        logger.info("Received request to register income");
        
        TransactionRequest secureRequest = new TransactionRequest(
            request.value(), 
            request.description(), 
            TransactionType.INCOME
        );

        Transactions createdTransaction = transactionService.registerTransaction(secureRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @PostMapping("/expense")
    public ResponseEntity<Transactions> registerExpense(@RequestBody TransactionRequest request) {
        logger.info("Received request to register expense");

        TransactionRequest secureRequest = new TransactionRequest(
            request.value(), 
            request.description(), 
            TransactionType.EXPENSE
        );

        Transactions createdTransaction = transactionService.registerTransaction(secureRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transactions>> getStatement(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        logger.info("Fetching statement from {} to {}", startDate, endDate);
        
        List<Transactions> statement = transactionService.getStatement(
            startDate.atStartOfDay(), 
            endDate.atTime(23, 59, 59)
        );
        
        return ResponseEntity.ok(statement);
    }
}