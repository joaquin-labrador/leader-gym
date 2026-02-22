package com.leadergym.control.service;

import com.leadergym.control.dto.PlanResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlanService {
    void updatePlanPrice(Long planId, Double newPrice);

    List<PlanResponseDTO> getsAllPlans();

    PlanResponseDTO getPlanById(int planId);
}