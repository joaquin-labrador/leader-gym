package com.leadergym.control.service.impl;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.common.mapper.PaymentHistoryMapper;
import com.leadergym.control.data.jpa.domain.specification.OtherPaymentsSpecifications;
import com.leadergym.control.data.jpa.domain.specification.PaymentHistorySpecifications;
import com.leadergym.control.dto.PaymentHistoryFilterDTO;
import com.leadergym.control.dto.PaymentHistoryResponseDTO;
import com.leadergym.control.entity.OtherPayments;
import com.leadergym.control.entity.PaymentHistory;
import com.leadergym.control.repository.OtherPaymentsRepository;
import com.leadergym.control.repository.PaymentHistoryRepository;
import com.leadergym.control.service.PaymentHistoryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final OtherPaymentsRepository otherPaymentsRepository;


    public PaymentHistoryServiceImpl(PaymentHistoryRepository repo, PaymentHistoryMapper mapper, OtherPaymentsRepository otherPaymentsRepository) {
        this.paymentHistoryRepository = repo;
        this.paymentHistoryMapper = mapper;
        this.otherPaymentsRepository = otherPaymentsRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentHistoryResponseDTO> getPaymentHistoryWithFilters(PaymentHistoryFilterDTO filter) {

        Specification<PaymentHistory> specNormal =
                PaymentHistorySpecifications.withFilters(filter, Constants.ARGENTINA_TIME_ZONE);

        List<PaymentHistoryResponseDTO> normalPayments = paymentHistoryRepository.findAll(specNormal)
                .stream()
                .map(ph -> {
                    PaymentHistoryResponseDTO dto = paymentHistoryMapper.toDto(ph);

                    // Como no usamos el campo real de la entidad, parseamos la fecha ya formateada del DTO
                    dto.setPaymentLocalDate(parsePaymentDate(dto.getPaymentDate()));

                    return dto;
                })
                .toList();

        Specification<OtherPayments> specExtra =
                OtherPaymentsSpecifications.withFilters(filter, Constants.ARGENTINA_TIME_ZONE);

        List<PaymentHistoryResponseDTO> extraPayments = otherPaymentsRepository.findAll(specExtra)
                .stream()
                .map(extra -> {
                    PaymentHistoryResponseDTO dto = new PaymentHistoryResponseDTO();
                    dto.setPaymentId(extra.getId());
                    dto.setMemberDni(extra.getMember().getDni());
                    dto.setAmountPaid(extra.getAmount().doubleValue());
                    dto.setPaymentMethod(extra.getPaymentMethod().name());
                    dto.setPlanDescription("PAGO SUELTO: " + extra.getDescription());

                    LocalDate paymentDate = extra.getPaymentDate();
                    dto.setPaymentLocalDate(paymentDate);
                    dto.setPaymentDate(paymentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                    return dto;
                })
                .toList();

        List<PaymentHistoryResponseDTO> combined = new ArrayList<>();
        combined.addAll(normalPayments);
        combined.addAll(extraPayments);

        combined.sort(
                Comparator.comparing(PaymentHistoryResponseDTO::getPaymentLocalDate, Comparator.reverseOrder())
                        .thenComparing(PaymentHistoryResponseDTO::getPaymentId, Comparator.reverseOrder())
        );

        return combined;
    }

    private LocalDate parsePaymentDate(String date) {
        if (date == null || date.isBlank()) {
            return LocalDate.MIN;
        }

        DateTimeFormatter ddMMyyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(date, ddMMyyyy);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(date, yyyyMMdd);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Formato de fecha no soportado: " + date, ex);
            }
        }
    }

}