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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        // 1. Pagos normales de membresías
        Specification<PaymentHistory> specNormal = PaymentHistorySpecifications.withFilters(filter, Constants.ARGENTINA_TIME_ZONE);
        List<PaymentHistoryResponseDTO> normalPayments = paymentHistoryRepository.findAll(specNormal)
                .stream()
                .map(paymentHistoryMapper::toDto)
                .toList();
        // 2. Pagos sueltos (extras)
        Specification<OtherPayments> specExtra = OtherPaymentsSpecifications.withFilters(filter, Constants.ARGENTINA_TIME_ZONE);
        List<PaymentHistoryResponseDTO> extraPayments = otherPaymentsRepository.findAll(specExtra)
                .stream()
                .map(extra -> {
                    PaymentHistoryResponseDTO dto = new PaymentHistoryResponseDTO();
                    dto.setPaymentId(extra.getId());
                    dto.setMemberDni(extra.getMember().getDni());
                    dto.setAmountPaid(extra.getAmount().doubleValue());
                    dto.setPaymentMethod(extra.getPaymentMethod().name());

                    // Le pasamos la descripción custom
                    dto.setPlanDescription("PAGO SUELTO: " + extra.getDescription());

                    // Si en el DTO usas String para la fecha, la formateás:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dto.setPaymentDate(extra.getPaymentDate().format(formatter));
                    return dto;
                })
                .toList();
        // 3. Unir ambas listas
        List<PaymentHistoryResponseDTO> combined = new ArrayList<>();
        combined.addAll(normalPayments);
        combined.addAll(extraPayments);
        // 4. Ordenar: más recientes primero (por fecha en formato string y1yyy-MM-dd / id)
        combined.sort((p1, p2) -> {
            int dateCompare = p2.getPaymentDate().compareTo(p1.getPaymentDate());
            if (dateCompare == 0) {
                return p2.getPaymentId().compareTo(p1.getPaymentId());
            }
            return dateCompare;
        });
        return combined;
    }

}