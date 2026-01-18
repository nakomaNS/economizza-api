package com.nakomans.economizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nakomans.economizza.model.RecurringExpenses;

public interface RecurringExpensesRepository extends JpaRepository<RecurringExpenses, Long> {
    List<RecurringExpenses> findByDayOfDueAndIsActiveTrue(Integer day);
}
