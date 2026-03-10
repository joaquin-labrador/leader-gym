package com.leadergym.control.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInResponseDTO {
    private String memberName;
    private Integer dayToExpirationMembership;
}
