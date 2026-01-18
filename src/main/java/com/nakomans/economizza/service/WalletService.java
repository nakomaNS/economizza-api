package com.nakomans.economizza.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.nakomans.economizza.repository.WalletRepository;
import com.nakomans.economizza.model.Wallet;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) { 
        this.walletRepository = walletRepository; 
    }

    private Wallet getCurrentWallet() {
        return walletRepository.findById(1L)
            .orElseGet(() -> {
                Wallet newWallet = new Wallet();
                newWallet.setCurrentBalance(BigDecimal.ZERO);
                newWallet.setTotalSaved(BigDecimal.ZERO);
                return walletRepository.save(newWallet);
            });
    }

    public BigDecimal getWalletAvailableBalance() {
        return getCurrentWallet().getCurrentBalance();
    }

    public void updateBalance(BigDecimal value) {
        Wallet wallet = getCurrentWallet();
        
        BigDecimal newBalance = wallet.getCurrentBalance().add(value);
        wallet.setCurrentBalance(newBalance);
        
        walletRepository.save(wallet);
    }
}