package com.nakomans.economizza.service;

import com.nakomans.economizza.dto.GoalDTO;
import com.nakomans.economizza.enums.GoalStatus;
import com.nakomans.economizza.enums.GoalType;
import com.nakomans.economizza.model.Goals;
import com.nakomans.economizza.model.Transactions;
import com.nakomans.economizza.repository.GoalRepository;
import com.nakomans.economizza.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @InjectMocks
    private GoalService goalService;

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("Deve atualizar saldo da meta, completar status e debitar da carteira")
    void shouldAddFundsAndCompleteGoal() {
        Long goalId = 1L;
        BigDecimal depositAmount = new BigDecimal("200.00");

        Goals mockGoal = new Goals();
        mockGoal.setId(goalId);
        mockGoal.setGoalName("PlayStation 5");
        mockGoal.setTargetAmount(new BigDecimal("1000.00"));
        mockGoal.setActualAmount(new BigDecimal("800.00"));
        mockGoal.setGoalStatus(GoalStatus.IN_PROGRESS);

        when(goalRepository.findById(goalId)).thenReturn(Optional.of(mockGoal));
        when(goalRepository.save(any(Goals.class))).thenReturn(mockGoal);

        Goals updatedGoal = goalService.addFundsToGoal(goalId, depositAmount);

        assertEquals(new BigDecimal("1000.00"), updatedGoal.getActualAmount());
        assertEquals(GoalStatus.COMPLETED, updatedGoal.getGoalStatus());
        verify(walletService, times(1)).updateBalance(new BigDecimal("-200.00"));
        verify(transactionRepository, times(1)).save(any(Transactions.class));
    }

    @Test
    @DisplayName("Deve criar meta corretamente")
    void shouldCreateGoal() {
        GoalDTO dto = new GoalDTO(
            "Carro Novo",
            new BigDecimal("50000"),
            BigDecimal.ZERO,
            LocalDate.now().plusYears(1),
            GoalStatus.IN_PROGRESS,
            GoalType.VEHICLE
        );

        when(goalRepository.save(any(Goals.class))).thenAnswer(i -> i.getArgument(0));

        Goals created = goalService.createGoal(dto);

        assertEquals(BigDecimal.ZERO, created.getActualAmount());
        assertEquals(GoalStatus.IN_PROGRESS, created.getGoalStatus());
        assertEquals("Carro Novo", created.getGoalName());
    }
}