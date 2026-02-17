package com.leadergym.control.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCredentialsDTO {
    private String dni;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long planId;
}
