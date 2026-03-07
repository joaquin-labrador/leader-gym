package com.leadergym.control.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberResponseDTO {
    private String dni;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean active;
    private String planDescription;
    private String expirationDate;
    private int age;
    private Long planId;
    private LocalDate birthDate;
}
