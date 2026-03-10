package com.leadergym.control.service;

import com.leadergym.control.dto.CheckInResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CheckInService {
    CheckInResponseDTO checkIn(String dni);
}