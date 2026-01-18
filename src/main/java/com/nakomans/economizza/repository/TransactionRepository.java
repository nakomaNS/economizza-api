package com.nakomans.economizza.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nakomans.economizza.model.Transactions;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}