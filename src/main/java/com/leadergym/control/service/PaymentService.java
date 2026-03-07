package com.leadergym.control.service;

import com.leadergym.control.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    void processPayment(String dni, Long planId, Double amount, String paymentMethod);

    List<PaymentResponseDTO> getPaymentHistoryByMember(String dni);

    void deleteLastPayment(Long id);
}

