package com.leadergym.control.controller;

import com.leadergym.control.dto.PaymentRequestDTO;
import com.leadergym.control.dto.PaymentResponseDTO;
import com.leadergym.control.service.PaymentService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> processPayment(@Valid @RequestBody @NotNull PaymentRequestDTO paymentRequest) {
        paymentService.processPayment(paymentRequest.getDni(), paymentRequest.getPlanId(), paymentRequest.getAmount(), paymentRequest.getPaymentMethod());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{dni}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentHistoryByMember(@NotNull @PathVariable String dni) {
        return ResponseEntity.ok(paymentService.getPaymentHistoryByMember(dni));
    }

    @DeleteMapping("/history/{dni}")
    public ResponseEntity<Void> deleteLastPayment(@NotNull @PathVariable Long id) {
        paymentService.deleteLastPayment(id);
        return ResponseEntity.ok().build();
    }
}
