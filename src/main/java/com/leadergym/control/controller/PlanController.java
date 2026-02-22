package com.leadergym.control.controller;

import com.leadergym.control.dto.PlanResponseDTO;
import com.leadergym.control.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PutMapping("/{planId}/price")
    public ResponseEntity<Void> updatePlanPrice(@PathVariable Long planId, @RequestParam Double newPrice) {
        planService.updatePlanPrice(planId, newPrice);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> getAllPlans() {
        return ResponseEntity.ok(planService.getsAllPlans());
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponseDTO> getPlanById(@PathVariable int planId) {
        return ResponseEntity.ok(planService.getPlanById(planId));
    }
}
