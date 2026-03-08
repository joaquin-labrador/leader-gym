package com.leadergym.control.service.impl;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.common.enums.PaymentMethod;
import com.leadergym.control.common.mapper.PaymentMapper;
import com.leadergym.control.dto.PaymentResponseDTO;
import com.leadergym.control.entity.Member;
import com.leadergym.control.entity.Payment;
import com.leadergym.control.entity.PaymentHistory;
import com.leadergym.control.entity.Plan;
import com.leadergym.control.exception.MemberNotFoundException;
import com.leadergym.control.exception.NotCorrectPaymentPlanException;
import com.leadergym.control.exception.PlanNotFoundException;
import com.leadergym.control.repository.MemberRepository;
import com.leadergym.control.repository.PaymentHistoryRepository;
import com.leadergym.control.repository.PaymentRepository;
import com.leadergym.control.repository.PlanRepository;
import com.leadergym.control.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Override
    public void processPayment(String dni, Long planId, Double amount, String paymentMethod) {
        Member member = memberRepository.findByDni(dni);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + planId));

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Payment payment = paymentRepository
                .findFirstByMemberDniAndPlanIdAndStateOrderByStartDateDescIdDesc(
                        dni,
                        planId,
                        Constants.PAYMENT_PARTIAL
                )
                .orElse(null);

        LocalDate today = LocalDate.now(Constants.ARGENTINA_TIME_ZONE);

        if (payment == null) {
            payment = new Payment();
            payment.setMember(member);
            payment.setPlan(plan);
            payment.setStartDate(today);
            payment.setEndDate(utilService.calculatePlanEndDate(today, plan.getDurationInDays()));
            payment.setAmountPaid(amount);
        } else {
            payment.setAmountPaid(payment.getAmountPaid() + amount);
        }

        // o false, según tu regla de negocio
        // o false, según tu regla de negocio
        if (payment.getAmountPaid() >= plan.getPrice()) {
            payment.setState(Constants.PAYMENT_COMPLETED);
        } else {
            payment.setState(Constants.PAYMENT_PARTIAL);
        }
        payment.setActive(true);
        member.setActive(true);
        member.setExpirationDate(payment.getEndDate());

        paymentRepository.save(payment);

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setPayment(payment);
        paymentHistory.setMemberDni(member.getDni());
        paymentHistory.setPlanName(plan.getCode());
        paymentHistory.setAmountPaid(amount);
        paymentHistory.setPaymentDate(today);
        paymentHistory.setPaymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()));
        paymentHistoryRepository.save(paymentHistory);

        if (member.getPlan() == null || !Objects.equals(member.getPlan().getId(), planId)) {
            member.setPlan(plan);
        }

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

    @Override
    public void deleteLastPayment(Long dni) {
        //Delete payment history
        PaymentHistory lastPaymentHistory = paymentHistoryRepository.findByPayment_Id(dni);
        if (lastPaymentHistory != null) {
            paymentHistoryRepository.delete(lastPaymentHistory);
        }
        //Delete payment
        Optional<Payment> lastPayment = paymentRepository.findById(dni);
        lastPayment.ifPresent(payment -> paymentRepository.delete(payment));
    }
}