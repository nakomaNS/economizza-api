package com.nakomans.economizza.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nakomans.economizza.dto.DepositRequest;
import com.nakomans.economizza.dto.GoalDTO;
import com.nakomans.economizza.model.Goals;
import com.nakomans.economizza.service.GoalService;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private static final Logger logger = LoggerFactory.getLogger(GoalController.class);
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<Goals> createGoal(@RequestBody GoalDTO dto) {
        logger.info("Request to create goal");
        Goals created = goalService.createGoal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Goals> deposit(@PathVariable Long id, @RequestBody DepositRequest request) {
        logger.info("Request to deposit {} to goal ID {}", request.amount(), id);
        Goals updatedGoal = goalService.addFundsToGoal(id, request.amount());
        return ResponseEntity.ok(updatedGoal);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Goals> withdraw(@PathVariable Long id, @RequestBody DepositRequest request) {
        logger.info("Request to withdraw {} from goal ID {}", request.amount(), id);
        Goals updatedGoal = goalService.withdrawFromGoal(id, request.amount());
        return ResponseEntity.ok(updatedGoal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goals> getGoal(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoal(id));
    }
}