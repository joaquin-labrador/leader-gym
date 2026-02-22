package com.leadergym.control.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReceiptsResponseDTO {
    private String dni;
    private String firstName;
    private String lastName;
    private String name;
    private Double amount;
    private LocalDate startDate;
    private LocalDate expirationDate;
}
