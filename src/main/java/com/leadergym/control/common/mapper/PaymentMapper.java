package com.leadergym.control.common.mapper;

import com.leadergym.control.dto.PaymentResponseDTO;
import com.leadergym.control.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {
    public static List<PaymentResponseDTO> toDtoList(List<Payment> payments) {
        if (payments == null) {
            return null;
        }
        return payments.stream()
                .map(payment -> {
                    PaymentResponseDTO dto = new PaymentResponseDTO();
                    dto.setDni(payment.getMember().getDni());
                    dto.setPlanName(payment.getPlan().getCode());
                    dto.setFirstName(payment.getMember().getFirstName());
                    dto.setLastName(payment.getMember().getLastName());
                    dto.setAmountPaid(payment.getAmountPaid());
                    dto.setEndDate(payment.getEndDate());
                    dto.setStartDate(payment.getStartDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
