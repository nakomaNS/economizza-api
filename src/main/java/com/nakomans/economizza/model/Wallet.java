package com.nakomans.economizza.model;

import java.math.BigDecimal;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "Wallet")
public class Wallet {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column(name = "current_balance", nullable = false)
private BigDecimal currentBalance = BigDecimal.ZERO;

@Column(name = "total_saved")
private BigDecimal totalSaved;

// CONSTRUTOR
public Wallet() { }

// SETTERS E GETTERS
public void setId(Long id) { this.id = id; }
public Long getId() { return id; }

public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
public BigDecimal getCurrentBalance() { return currentBalance; }

public void setTotalSaved(BigDecimal totalSaved) {this.totalSaved = totalSaved; }
public BigDecimal getTotalSaved() { return totalSaved; }

}