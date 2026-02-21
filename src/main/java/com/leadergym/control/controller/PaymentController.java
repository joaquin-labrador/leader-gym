package com.leadergym.control.controller;

import com.leadergym.control.dto.PaymentRequestDTO;
import com.leadergym.control.service.PaymentService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> processPayment(@Valid @RequestBody @NotNull PaymentRequestDTO paymentRequest) {
        paymentService.processPayment(paymentRequest.getDni(), paymentRequest.getPlanId(), paymentRequest.getAmount());
        return ResponseEntity.ok().build();
    }
}
