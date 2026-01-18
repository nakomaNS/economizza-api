package com.nakomans.economizza.dto;

import java.math.BigDecimal;
import com.nakomans.economizza.enums.TransactionType;

public record TransactionRequest(BigDecimal value, String description, TransactionType type) {}

