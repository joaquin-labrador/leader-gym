package com.leadergym.control.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentHistoryResponseDTO {
    private String memberDni;
    private String planDescription;
    private double amountPaid;
    private String paymentMethod;
    private String paymentDate;
}
