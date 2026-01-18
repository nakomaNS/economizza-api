package com.nakomans.economizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nakomans.economizza.model.Goals;

public interface GoalRepository extends JpaRepository<Goals, Long> {
}