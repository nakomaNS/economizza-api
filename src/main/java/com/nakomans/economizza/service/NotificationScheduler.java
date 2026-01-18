package com.nakomans.economizza.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.nakomans.economizza.model.RecurringExpenses;

@Component
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    private final RecurringExpenseService recurringService;
    private final TransactionService transactionService;

    public NotificationScheduler(RecurringExpenseService recurringService, TransactionService transactionService) {
        this.recurringService = recurringService;
        this.transactionService = transactionService;
    }

    @Scheduled(cron = "0 0 0 * * *") 
    public void checkDailyBills() {
        logger.info("Starting daily recurring expenses check");

        List<RecurringExpenses> dueExpenses = recurringService.findExpensesDueToday();

        if (dueExpenses.isEmpty()) {
            logger.info("No recurring expenses due today");
            return;
        }

        logger.info("Found {} expenses to process", dueExpenses.size());

        for (RecurringExpenses expense : dueExpenses) {
            try {
                transactionService.processRecurringExpense(expense);
                logger.info("Successfully processed expense: {} | Amount: {}", expense.getExpenseTitle(), expense.getFixedAmount());
            } catch (Exception e) {
                logger.error("Failed to process expense: {}", expense.getExpenseTitle(), e);
            }
        }

        logger.info("Daily recurring expenses check completed");
    }
}