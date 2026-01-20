package com.nakomans.economizza.service;

import com.nakomans.economizza.dto.TransactionRequest;
import com.nakomans.economizza.enums.TransactionType;
import com.nakomans.economizza.model.Transactions;
import com.nakomans.economizza.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    @DisplayName("Deve registrar despesa convertendo valor para negativo")
    void shouldRegisterExpenseAsNegative() {
        TransactionRequest request = new TransactionRequest(
            new BigDecimal("50.00"),
            "Lanche",
            TransactionType.EXPENSE
        );

        when(transactionRepository.save(any(Transactions.class))).thenAnswer(i -> i.getArgument(0));

        Transactions result = transactionService.registerTransaction(request);

        assertEquals(new BigDecimal("-50.00"), result.getAmount());
        verify(walletService).updateBalance(new BigDecimal("-50.00"));
    }
}