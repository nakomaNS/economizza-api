package com.nakomans.economizza.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nakomans.economizza.enums.TransactionType;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transactions {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "amount")
private BigDecimal amount;

@Enumerated(EnumType.STRING)
@Column(name = "type")
private TransactionType type;

@Column(name = "description")
private String description;

@Column(name = "timestamp")
private LocalDateTime timestamp;

@ManyToOne
@JoinColumn(name = "related_goal_id")
private Goals relatedGoal;

@ManyToOne
@JoinColumn(name = "related_expense_id")
private RecurringExpenses relatedExpense;

// CONSTRUTOR
public Transactions() {}

// SETTERS E GETTERS
public void setId(Long id) { this.id = id; }
public Long getId() { return id; }

public void setAmount(BigDecimal amount) { this.amount = amount; }
public BigDecimal getAmount() { return amount; }

public void setType(TransactionType type) { this.type = type; }
public TransactionType getType() { return type; }

public void setDescription(String description) { this.description = description; }
public String getDescription() { return description; }

public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
public LocalDateTime getTimestamp() { return timestamp; }

public void setRelatedGoal(Goals relatedGoal) { this.relatedGoal = relatedGoal; }
public Goals getRelatedGoal() { return relatedGoal; }

public void setRelatedExpense(RecurringExpenses relatedExpense) { this.relatedExpense = relatedExpense; }
public RecurringExpenses getRelatedExpense() { return relatedExpense; }


}
