package com.leadergym.control.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlanResponseDTO {
    private String name;
    private Double price;
}
