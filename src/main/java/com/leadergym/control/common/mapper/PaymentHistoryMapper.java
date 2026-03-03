package com.leadergym.control.common.mapper;

import com.leadergym.control.dto.PaymentHistoryResponseDTO;
import com.leadergym.control.entity.PaymentHistory;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Component
public class PaymentHistoryMapper {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PaymentHistoryResponseDTO toDto(PaymentHistory ph) {
        if (ph == null) return null;

        return PaymentHistoryResponseDTO.builder()
                .memberDni(ph.getMemberDni())
                .planDescription(ph.getPlanName())
                .amountPaid(ph.getAmountPaid())
                .paymentMethod(ph.getPaymentMethod() != null ? ph.getPaymentMethod().name() : null)
                .paymentDate(ph.getPaymentDate() != null ? ph.getPaymentDate().format(DATE_FMT) : null)
                .build();
    }

    public List<PaymentHistoryResponseDTO> toDtoList(List<PaymentHistory> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();
        return list.stream().map(this::toDto).toList();
    }
}