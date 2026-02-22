package com.leadergym.control.dto;

import lombok.*;

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
    private double amountPaid;
}
