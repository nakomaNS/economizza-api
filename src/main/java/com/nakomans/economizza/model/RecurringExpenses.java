package com.nakomans.economizza.model;

import java.math.BigDecimal;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "RecurringExpenses")
public class RecurringExpenses {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "expense_title")
private String expenseTitle;

@Column(name = "day_of_due")
private Integer dayOfDue;

@Column(name = "fixed_amount")
private BigDecimal fixedAmount;

@Column(name = "category")
private String category;

@Column(name = "is_active")
private Boolean isActive;

// CONSTRUTOR
public RecurringExpenses() { }

// SETTERS E GETTERS
public void setId(Long id) { this.id = id; }
public Long getId() { return id; }

public void setExpenseTitle(String expenseTitle) { this.expenseTitle = expenseTitle; }
public String getExpenseTitle() { return expenseTitle; }

public void setDayOfDue(Integer dayOfDue) { this.dayOfDue = dayOfDue; }
public Integer getDayOfDue() { return dayOfDue; }

public void setFixedAmount(BigDecimal fixedAmount) { this.fixedAmount = fixedAmount; }
public BigDecimal getFixedAmount() { return fixedAmount; }

public void setCategory(String category) { this.category = category; }
public String getCategory() { return category; }

public void setIsActive(Boolean isActive) { this.isActive = isActive; }
public Boolean getIsActive() { return this.isActive; }
}