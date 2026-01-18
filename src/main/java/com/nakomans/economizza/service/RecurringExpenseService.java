package com.nakomans.economizza.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nakomans.economizza.dto.ExpenseDTO;
import com.nakomans.economizza.model.RecurringExpenses;
import com.nakomans.economizza.repository.RecurringExpensesRepository;

@Service
public class RecurringExpenseService {
private final RecurringExpensesRepository repository;
public RecurringExpenseService(RecurringExpensesRepository repository) { this.repository = repository; }

public RecurringExpenses createExpense(ExpenseDTO data) {
    if (data.dayOfDue() < 1 || data.dayOfDue() > 31) {
        throw new IllegalArgumentException("Day of due must be between 1 and 31.");
    }

    RecurringExpenses expensesModel = new RecurringExpenses();
    expensesModel.setExpenseTitle(data.expenseTitle());
    expensesModel.setDayOfDue(data.dayOfDue());
    expensesModel.setFixedAmount(data.fixedAmount());
    expensesModel.setCategory(data.category());
    expensesModel.setIsActive(data.isActive() != null ? data.isActive() : true);

    return repository.save(expensesModel);
}

public List<RecurringExpenses> findExpensesDueToday() {
    return repository.findByDayOfDueAndIsActiveTrue(LocalDate.now().getDayOfMonth());
}

public void toggleExpenseActive(Long id) {
    RecurringExpenses expense = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

    expense.setIsActive(!expense.getIsActive());


    repository.save(expense);
}

}