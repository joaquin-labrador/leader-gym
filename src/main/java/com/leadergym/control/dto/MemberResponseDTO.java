package com.leadergym.control.dto;

import lombok.*;

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
}
