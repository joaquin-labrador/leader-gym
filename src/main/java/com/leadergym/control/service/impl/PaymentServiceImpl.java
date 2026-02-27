package com.leadergym.control.service.impl;

import com.leadergym.control.common.mapper.PaymentMapper;
import com.leadergym.control.dto.PaymentResponseDTO;
import com.leadergym.control.entity.Member;
import com.leadergym.control.entity.Payment;
import com.leadergym.control.entity.Plan;
import com.leadergym.control.exception.MemberNotFoundException;
import com.leadergym.control.exception.NotCorrectPaymentPlanException;
import com.leadergym.control.exception.PlanNotFoundException;
import com.leadergym.control.repository.MemberRepository;
import com.leadergym.control.repository.PaymentRepository;
import com.leadergym.control.repository.PlanRepository;
import com.leadergym.control.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private UtilService utilService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void processPayment(String dni, Long planId, Double amount) {
        Member member = memberRepository.findByDni(dni);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + planId));
        if (amount != plan.getPrice()) {
            throw new NotCorrectPaymentPlanException("The amount does not match the plan price. Expected: " + plan.getPrice() + ", Received: " + amount);
        }
        Payment payment = new Payment();
        payment.setMember(member);
        payment.setPlan(plan);
        payment.setStartDate(LocalDate.now());
        payment.setEndDate(utilService.calculatePlanEndDate(payment.getStartDate(), plan.getDurationInDays()));
        payment.setActive(true);
        payment.setAmountPaid(amount);
        paymentRepository.save(payment);

        member.setActive(true);
        member.setExpirationDate(payment.getEndDate());
        memberRepository.save(member);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentHistoryByMember(String dni) {
        Member member = memberRepository.findByDni(dni);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }
        List<Payment> paymentListByMember = paymentRepository.findByMemberId(member.getId());
        if (paymentListByMember.isEmpty()) {
            throw new MemberNotFoundException("No payments found for member with DNI: " + dni);
        }
        return PaymentMapper.toDtoList(paymentListByMember);
    }
}