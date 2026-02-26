package com.leadergym.control.service;

import com.leadergym.control.dto.ReceiptsResponseDTO;
import com.leadergym.control.entity.Receipts;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReceiptsService {
    List<ReceiptsResponseDTO> getHistoryReceiptsByMember(String dni);
}
