package com.nakomans.economizza.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.nakomans.economizza.enums.GoalStatus;
import com.nakomans.economizza.enums.GoalType;

public record GoalDTO(String goalName, BigDecimal targetAmount, BigDecimal actualAmount, LocalDate deadline, GoalStatus status, GoalType goalType) {}
