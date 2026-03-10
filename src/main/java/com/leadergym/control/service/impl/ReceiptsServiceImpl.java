package com.leadergym.control.service.impl;

import com.leadergym.control.common.mapper.ReceiptsMapper;
import com.leadergym.control.dto.ReceiptsResponseDTO;
import com.leadergym.control.entity.Receipts;
import com.leadergym.control.exception.ReceiptsNotFound;
import com.leadergym.control.repository.ReceiptsRepository;
import com.leadergym.control.service.ReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReceiptsServiceImpl implements ReceiptsService {
    @Autowired
    private ReceiptsRepository receiptsRepository;

    @Override
    public List<ReceiptsResponseDTO> getHistoryReceiptsByMember(String dni) {
        List<Receipts> receiptsListBeMember = receiptsRepository.findByMemberDni(dni);
        if (receiptsListBeMember.isEmpty()) {
            throw new ReceiptsNotFound("No receipts found for member with DNI: " + dni);
        }
        return ReceiptsMapper.toDtoList(receiptsListBeMember);
    }

    @Override
    public List<ReceiptsResponseDTO> getHistoryByDate(LocalDate date) {
        List<Receipts> receiptsListByDate = receiptsRepository.findByDate(date);
        if (receiptsListByDate.isEmpty()) {
            throw new ReceiptsNotFound("No receipts found for date: " + date);
        }
        return ReceiptsMapper.toDtoList(receiptsListByDate);
    }
}
