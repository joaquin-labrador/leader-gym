package com.leadergym.control.service.impl;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.common.mapper.PaymentHistoryMapper;
import com.leadergym.control.data.jpa.domain.specification.PaymentHistorySpecifications;
import com.leadergym.control.dto.PaymentHistoryFilterDTO;
import com.leadergym.control.dto.PaymentHistoryResponseDTO;
import com.leadergym.control.entity.PaymentHistory;
import com.leadergym.control.repository.PaymentHistoryRepository;
import com.leadergym.control.service.PaymentHistoryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;


    public PaymentHistoryServiceImpl(PaymentHistoryRepository repo, PaymentHistoryMapper mapper) {
        this.paymentHistoryRepository = repo;
        this.paymentHistoryMapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentHistoryResponseDTO> getPaymentHistoryWithFilters(PaymentHistoryFilterDTO filter) {

        Specification<PaymentHistory> spec =
                PaymentHistorySpecifications.withFilters(filter, Constants.ARGENTINA_TIME_ZONE);

        Sort sort = Sort.by(Sort.Direction.DESC, "paymentDate")
                .and(Sort.by(Sort.Direction.DESC, "id"));

        return paymentHistoryRepository.findAll(spec, sort)
                .stream()
                .map(paymentHistoryMapper::toDto) // OJO: instancia, no PaymentHistoryMapper::toDto
                .toList();
    }
}