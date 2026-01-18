package com.nakomans.economizza.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.nakomans.economizza.enums.GoalStatus;
import com.nakomans.economizza.enums.GoalType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Goals")
public class Goals {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Long id;

@Column(name = "goal_name", nullable = false)
private String goalName;

@Column(name = "target_amount")
private BigDecimal targetAmount = BigDecimal.ZERO;

@Column(name = "goal_type")
private GoalType goalType;

@Column(name = "actual_amount", nullable = false)
private BigDecimal actualAmount = BigDecimal.ZERO;

@Column(name = "deadline")
private LocalDate deadline;

@Enumerated(EnumType.STRING)
@Column(name = "status")
private GoalStatus status;

// CONSTRUTOR
public Goals(){ }

// SETTERS E GETTERS
public void setId(Long id) { this.id = id; }
public Long getId() { return id; }

public void setGoalName(String goalName) { this.goalName = goalName; }
public String getGoalName() { return goalName; }

public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
public BigDecimal getTargetAmount() { return targetAmount; }

public void setGoalType(GoalType goalType) { this.goalType = goalType; }
public GoalType getGoalType() { return goalType; }

public void setActualAmount(BigDecimal actualAmount) { this.actualAmount = actualAmount; }
public BigDecimal getActualAmount() { return actualAmount; }

public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
public LocalDate getDeadline() { return deadline; }

public void setGoalStatus(GoalStatus status) { this.status = status; }
public GoalStatus getGoalStatus() { return status; }

}