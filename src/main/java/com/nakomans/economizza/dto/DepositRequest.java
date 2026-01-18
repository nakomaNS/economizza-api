package com.nakomans.economizza.dto;

import java.math.BigDecimal;

public record DepositRequest(
    BigDecimal amount
) {}