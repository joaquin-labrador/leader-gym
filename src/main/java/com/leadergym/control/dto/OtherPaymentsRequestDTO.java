package com.leadergym.control.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtherPaymentsRequestDTO {
    @NotEmpty(message = "Member DNI is required")
    private String memberDni;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    @NotEmpty(message = "Payment method is required")
    private String paymentMethod;
}
