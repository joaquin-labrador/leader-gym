package com.leadergym.control.controller;

import com.leadergym.control.dto.PaymentHistoryFilterDTO;
import com.leadergym.control.dto.PaymentHistoryResponseDTO;
import com.leadergym.control.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment/history")
public class PaymentHistoryController {
    @Autowired
    private PaymentHistoryService paymentHistoryService;

    @PostMapping("/filter")
    public ResponseEntity<List<PaymentHistoryResponseDTO>> getPaymentHistoryWithFilters(@RequestBody PaymentHistoryFilterDTO filter) {
        List<PaymentHistoryResponseDTO> history = paymentHistoryService.getPaymentHistoryWithFilters(filter);
        return ResponseEntity.ok(history);
    }
}
