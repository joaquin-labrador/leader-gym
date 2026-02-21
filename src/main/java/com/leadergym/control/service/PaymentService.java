package com.leadergym.control.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
     void processPayment(String dni, Long planId, Double amount);
}