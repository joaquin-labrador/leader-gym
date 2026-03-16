package com.leadergym.control.service;

import com.leadergym.control.dto.OtherPaymentsRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface OtherPaymentsService {
    void createOtherPayment(OtherPaymentsRequestDTO otherPaymentsRequestDTO);
}
