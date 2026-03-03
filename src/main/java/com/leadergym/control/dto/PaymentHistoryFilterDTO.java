package com.leadergym.control.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentHistoryFilterDTO {
    private String memberDni;
    private String paymentMethod;
    private Date startDate;
    private Date endDate;
}
