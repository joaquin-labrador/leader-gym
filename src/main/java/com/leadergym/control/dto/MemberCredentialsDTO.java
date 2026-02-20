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
public class MemberCredentialsDTO {
    @NotBlank(message = "DNI is required")
    private String dni;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Plan ID cannot be null")
    @Positive(message = "Plan ID must be a positive number")
    private Long planId;
    @NotBlank(message = "Date of birth cannot be blank")
    private String birthDate;
}
