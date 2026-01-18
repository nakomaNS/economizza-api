package com.nakomans.economizza.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.nakomans.economizza.dto.TransactionRequest;
import com.nakomans.economizza.enums.TransactionType;
import com.nakomans.economizza.model.RecurringExpenses;
import com.nakomans.economizza.model.Transactions;
import com.nakomans.economizza.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final WalletService walletService;
    private final TransactionRepository transactionRepository;

    public TransactionService(WalletService walletService, TransactionRepository transactionRepository) {
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transactions registerTransaction(TransactionRequest request) {
        logger.info("Attempting to register new transaction. Description: {}", request.description());

        if (request.value() == null || request.value().compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Invalid transaction amount: {}", request.value());
            throw new IllegalArgumentException("Transaction amount must be positive.");
        }

        if (request.type() == null) {
            logger.error("Transaction type is missing.");
            throw new IllegalArgumentException("Transaction type cannot be null.");
        }

        BigDecimal finalAmount = request.type() == TransactionType.EXPENSE 
            ? request.value().abs().negate() 
            : request.value().abs();

        Transactions transaction = new Transactions();
        transaction.setAmount(finalAmount);
        transaction.setDescription(request.description());
        transaction.setType(request.type());
        transaction.setTimestamp(LocalDateTime.now());

        Transactions savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction saved successfully. ID: {}", savedTransaction.getId());

        walletService.updateBalance(finalAmount);
        logger.info("Wallet balance updated successfully.");

        return savedTransaction;
    }

    @Transactional
    public void processRecurringExpense(RecurringExpenses expense) {
        logger.info("Processing recurring expense: {}", expense.getExpenseTitle());

        if (expense.getFixedAmount() == null || expense.getFixedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Invalid expense amount for recurring expense ID: {}", expense.getId());
            throw new IllegalArgumentException("Recurring expense amount must be positive.");
        }

        BigDecimal debtAmount = expense.getFixedAmount().abs().negate();

        Transactions transaction = new Transactions();
        transaction.setAmount(debtAmount);
        transaction.setDescription("Auto Debit: " + expense.getExpenseTitle());
        transaction.setType(TransactionType.EXPENSE);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        logger.info("Recurring transaction saved. Description: {}", transaction.getDescription());

        walletService.updateBalance(debtAmount);
        logger.info("Wallet balance deducted for recurring expense.");
    }

    public List<Transactions> getStatement(LocalDateTime start, LocalDateTime end) {
        logger.info("Fetching statement from {} to {}", start, end);

        if (start.isAfter(end)) {
            logger.error("Invalid date range: Start date {} is after End date {}", start, end);
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        List<Transactions> transactions = transactionRepository.findByTimestampBetween(start, end);
        logger.info("Found {} transactions in the given period.", transactions.size());
        
        return transactions;
    }
}