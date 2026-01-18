package com.nakomans.economizza.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nakomans.economizza.dto.ExpenseDTO;
import com.nakomans.economizza.model.RecurringExpenses;
import com.nakomans.economizza.service.RecurringExpenseService;

@RestController
@RequestMapping("/api/recurring-expenses")
public class RecurringExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(RecurringExpenseController.class);
    private final RecurringExpenseService recurringService;

    public RecurringExpenseController(RecurringExpenseService recurringService) {
        this.recurringService = recurringService;
    }

    @PostMapping
    public ResponseEntity<RecurringExpenses> createExpense(@RequestBody ExpenseDTO dto) {
        logger.info("Request to create recurring expense: {}", dto.expenseTitle());
        RecurringExpenses created = recurringService.createExpense(dto); 
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleActive(@PathVariable Long id) {
        logger.info("Request to toggle expense ID: {}", id);
        recurringService.toggleExpenseActive(id);
        return ResponseEntity.noContent().build();
    }
    
}