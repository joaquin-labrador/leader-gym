package com.leadergym.control.service.impl;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.common.enums.PaymentMethod;
import com.leadergym.control.dto.OtherPaymentsRequestDTO;
import com.leadergym.control.entity.Member;
import com.leadergym.control.entity.OtherPayments;
import com.leadergym.control.exception.MemberNotFoundException;
import com.leadergym.control.repository.MemberRepository;
import com.leadergym.control.repository.OtherPaymentsRepository;
import com.leadergym.control.service.OtherPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OtherPaymentsServiceImpl implements OtherPaymentsService {
    @Autowired
    private OtherPaymentsRepository otherPaymentsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public void createOtherPayment(OtherPaymentsRequestDTO otherPaymentsRequestDTO) {
        Member member = memberRepository.findByDni(otherPaymentsRequestDTO.getMemberDni());
        if (member == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + otherPaymentsRequestDTO.getMemberDni());
        }
        OtherPayments otherPayments = new OtherPayments();
        otherPayments.setDescription(otherPaymentsRequestDTO.getDescription());
        otherPayments.setAmount(otherPaymentsRequestDTO.getAmount());
        otherPayments.setPaymentDate(LocalDate.now(Constants.ARGENTINA_TIME_ZONE));
        otherPayments.setPaymentMethod(PaymentMethod.valueOf(otherPaymentsRequestDTO.getPaymentMethod()));
        otherPayments.setMember(member);
        otherPaymentsRepository.save(otherPayments);
    }
}
