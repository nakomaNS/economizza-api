package com.nakomans.economizza.service;

import com.nakomans.economizza.model.Wallet;
import com.nakomans.economizza.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Deve retornar saldo da carteira")
    void shouldReturnBalanceFromExistingWallet() {
        Wallet existingWallet = new Wallet();
        existingWallet.setId(1L);
        existingWallet.setCurrentBalance(new BigDecimal("150.00"));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(existingWallet));

        BigDecimal balance = walletService.getWalletAvailableBalance();

        assertEquals(new BigDecimal("150.00"), balance);
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    @DisplayName("Deve criar uma nova carteira se não existir ao consultar saldo")
    void shouldCreateWalletWhenNoneExists() {
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        when(walletRepository.save(any(Wallet.class))).thenAnswer(invocation -> {
            Wallet w = invocation.getArgument(0);
            w.setId(1L);
            return w;
        });

        BigDecimal balance = walletService.getWalletAvailableBalance();

        assertEquals(BigDecimal.ZERO, balance);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }
}