package com.leadergym.control.service.impl;

import com.leadergym.control.dto.ReceiptsResponseDTO;
import com.leadergym.control.service.ReceiptsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptsServiceImpl implements ReceiptsService {

    @Override
    public List<ReceiptsResponseDTO> getHistoryReceiptsByMember(String dni) {
        return List.of();
    }
}
