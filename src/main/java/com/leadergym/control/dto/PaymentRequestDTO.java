package com.leadergym.control.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentRequestDTO {
    @NotBlank(message = "DNI is required")
    private String dni;
    @NotNull(message = "Plan ID cannot be null")
    private Long planId;
    @Positive(message = "Amount must be a positive number")
    private Double amount;
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    @NotNull
    private LocalDate paymentDate;
}
