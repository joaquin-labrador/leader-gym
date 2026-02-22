package com.leadergym.control.common.mapper;

import com.leadergym.control.dto.PlanResponseDTO;
import com.leadergym.control.entity.Plan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanMapper {

    public static PlanResponseDTO toDto(Plan plan) {
        if (plan == null) {
            return null;
        }
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setName(plan.getCode());
        dto.setPrice(plan.getPrice());
        // añadir más campos según la entidad/DTO
        return dto;
    }

    public static List<PlanResponseDTO> toDtoList(List<Plan> plans) {
        if (plans == null) {
            return null;
        }
        return plans.stream()
                .map(PlanMapper::toDto)
                .collect(Collectors.toList());
    }

}