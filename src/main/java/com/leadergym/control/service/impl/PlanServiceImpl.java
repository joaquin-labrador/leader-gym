package com.leadergym.control.service.impl;

import com.leadergym.control.common.mapper.PlanMapper;
import com.leadergym.control.dto.PlanResponseDTO;
import com.leadergym.control.entity.Plan;
import com.leadergym.control.exception.PlanNotFoundException;
import com.leadergym.control.repository.PlanRepository;
import com.leadergym.control.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    public PlanRepository planRepository;


    @Override
    public void updatePlanPrice(Long planId, Double newPrice) {
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new PlanNotFoundException("Plan not found"));
        if (newPrice == null || newPrice <= 0) {
            throw new IllegalArgumentException("Invalid price value");
        }
        plan.setPrice(newPrice);
        planRepository.save(plan);
    }

    @Override
    public List<PlanResponseDTO> getsAllPlans() {
        List<Plan> plans = planRepository.findAll();
        if (plans.isEmpty()) {
            throw new PlanNotFoundException("No plans found");
        }
        return PlanMapper.toDtoList(plans);

    }

    @Override
    public PlanResponseDTO getPlanById(int planId) {
        Plan plan = planRepository.findById((long) planId).orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + planId));
        return PlanMapper.toDto(plan);
    }
}
