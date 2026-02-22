package com.leadergym.control.controller;

import com.leadergym.control.dto.ReceiptsResponseDTO;
import com.leadergym.control.service.ReceiptsService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptsController {
    @Autowired
    private ReceiptsService receiptsService;

    @GetMapping("/history/{dni}")
    public ResponseEntity<List<ReceiptsResponseDTO>> getHistoryReceiptsByMember(@NotNull @PathVariable String dni) {
        return ResponseEntity.ok(receiptsService.getHistoryReceiptsByMember(dni));
    }
}
