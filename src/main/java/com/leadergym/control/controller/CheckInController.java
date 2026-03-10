package com.leadergym.control.controller;

import com.leadergym.control.dto.CheckInResponseDTO;
import com.leadergym.control.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkIn")
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    @PostMapping("/{dni}")
    public ResponseEntity<CheckInResponseDTO> checkIn(@PathVariable String dni) {
        return ResponseEntity.ok(checkInService.checkIn(dni));
    }
}
