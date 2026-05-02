package com.leadergym.control.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateCredentialsDTO {
    @NotBlank(message = "First name is required")
    @NotNull(message = "First name cannot be null")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @NotNull(message = "Last name cannot be null")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @Positive(message = "Plan ID must be a positive number")
    @NotNull(message = "Plan ID cannot be null")
    private Long planId;
    @NotBlank(message = "Birth date is required")
    private String birthDate;
    @NotBlank(message = "DNI is required")
    private String dni;
}
