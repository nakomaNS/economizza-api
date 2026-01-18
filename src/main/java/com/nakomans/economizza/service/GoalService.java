package com.nakomans.economizza.service;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.nakomans.economizza.dto.GoalDTO;
import com.nakomans.economizza.enums.GoalStatus;
import com.nakomans.economizza.enums.TransactionType;
import com.nakomans.economizza.model.Goals;
import com.nakomans.economizza.model.Transactions;
import com.nakomans.economizza.repository.GoalRepository;
import com.nakomans.economizza.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class GoalService {

    private static final Logger logger = LoggerFactory.getLogger(GoalService.class);

    private final GoalRepository goalRepository;
    private final WalletService walletService;
    private final TransactionRepository transactionRepository;

    public GoalService(GoalRepository goalRepository, WalletService walletService, TransactionRepository transactionRepository) {
        this.goalRepository = goalRepository;
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
    }

    public Goals createGoal(GoalDTO data) {
        logger.info("Creating new goal: {}", data.goalName());
        
        Goals goal = new Goals();
        goal.setGoalName(data.goalName());
        goal.setTargetAmount(data.targetAmount());
        goal.setActualAmount(BigDecimal.ZERO);
        goal.setDeadline(data.deadline());
        goal.setGoalType(data.goalType());
        goal.setGoalStatus(GoalStatus.IN_PROGRESS);
        
        return goalRepository.save(goal);
    }

    @Transactional
    public Goals addFundsToGoal(Long goalId, BigDecimal amount) {
        logger.info("Adding funds to goal ID: {}", goalId);

        Goals goal = goalRepository.findById(goalId)
            .orElseThrow(() -> new RuntimeException("Goal not found"));

        walletService.updateBalance(amount.negate());

        goal.setActualAmount(goal.getActualAmount().add(amount));
        updateGoalStatus(goal);
        
        Goals updatedGoal = goalRepository.save(goal);

        Transactions transaction = new Transactions();
        transaction.setAmount(amount.negate());
        transaction.setDescription("Deposit to Goal: " + goal.getGoalName());
        transaction.setType(TransactionType.EXPENSE); 
        transaction.setRelatedGoal(goal); 
        
        transactionRepository.save(transaction);
        logger.info("Funds added successfully. New Goal Balance: {}", updatedGoal.getActualAmount());

        return updatedGoal;
    }

    @Transactional
    public Goals withdrawFromGoal(Long goalId, BigDecimal amount) {
        logger.info("Withdrawing from goal ID: {}", goalId);

        Goals goal = goalRepository.findById(goalId)
            .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (goal.getActualAmount().compareTo(amount) < 0) {
            logger.error("Insufficient funds in goal ID: {}", goalId);
            throw new IllegalArgumentException("Insufficient funds in goal.");
        }

        walletService.updateBalance(amount);

        goal.setActualAmount(goal.getActualAmount().subtract(amount));
        updateGoalStatus(goal);

        Goals updatedGoal = goalRepository.save(goal);

        Transactions transaction = new Transactions();
        transaction.setAmount(amount);
        transaction.setDescription("Withdraw from Goal: " + goal.getGoalName());
        transaction.setType(TransactionType.INCOME);
        transaction.setRelatedGoal(goal);

        transactionRepository.save(transaction);
        logger.info("Withdrawal successful. New Goal Balance: {}", updatedGoal.getActualAmount());

        return updatedGoal;
    }

    public Goals getGoal(Long id) {
        return goalRepository.findById(id).orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    private void updateGoalStatus(Goals goal) {
        if (goal.getActualAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setGoalStatus(GoalStatus.COMPLETED);
        } else {
            goal.setGoalStatus(GoalStatus.IN_PROGRESS);
        }
    }
}