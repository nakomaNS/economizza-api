package com.nakomans.economizza.dto;

import java.math.BigDecimal;

public record ExpenseDTO(String expenseTitle, Integer dayOfDue, BigDecimal fixedAmount, String category, Boolean isActive) {
    
}
