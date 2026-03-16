package com.leadergym.control.controller;

import com.leadergym.control.dto.OtherPaymentsRequestDTO;
import com.leadergym.control.service.OtherPaymentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/other-payments")
public class OtherPaymentsController {

    @Autowired
    private OtherPaymentsService otherPaymentsService;

    @PostMapping()
    ResponseEntity<Void> createOtherPayment(@Valid @RequestBody OtherPaymentsRequestDTO request) {
        otherPaymentsService.createOtherPayment(request);
        return ResponseEntity.ok().build();
    }

}
