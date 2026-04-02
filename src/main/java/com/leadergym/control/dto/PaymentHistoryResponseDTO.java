package com.leadergym.control.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentHistoryResponseDTO {
    private Long paymentId;
    private String memberDni;
    private Double amountPaid;
    private String paymentMethod;
    private String planDescription;
    private String paymentDate;

    @JsonIgnore
    private LocalDate paymentLocalDate;
}