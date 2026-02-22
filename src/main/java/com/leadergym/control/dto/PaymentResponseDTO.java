package com.leadergym.control.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponseDTO {
    private String dni;
    private String firstName;
    private String lastName;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double amountPaid;
}
