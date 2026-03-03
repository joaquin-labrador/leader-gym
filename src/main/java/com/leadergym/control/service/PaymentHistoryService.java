package com.leadergym.control.service;

import com.leadergym.control.dto.PaymentHistoryFilterDTO;
import com.leadergym.control.dto.PaymentHistoryResponseDTO;
import com.leadergym.control.entity.PaymentHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentHistoryService {
    List<PaymentHistoryResponseDTO> getPaymentHistoryWithFilters(PaymentHistoryFilterDTO filter);
}
