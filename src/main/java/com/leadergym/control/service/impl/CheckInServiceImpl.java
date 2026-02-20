package     com.leadergym.control.service.impl;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.entity.*;
import com.leadergym.control.exception.MemberNotFoundException;
import com.leadergym.control.exception.MemberNotPayException;
import com.leadergym.control.exception.PlanNotFoundException;
import com.leadergym.control.exception.WeeklyVisitLimitExceededException;
import com.leadergym.control.repository.*;
import com.leadergym.control.service.CheckInService;
import com.leadergym.control.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckInServiceImpl implements CheckInService {

    private final static Integer MAX_CHECKINS_PER_WEEK = 3;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private ReceiptsRepository receiptsRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UtilService utilService;



    @Override
    public void checkIn(String dni) {
        Member member = memberRepository.findByDni(dni);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }
        //buscar el ultimo recibo del miembro activo
        Payment lastPayment = paymentRepository.findTopByMember_DniAndActiveTrueOrderByEndDateDesc(dni)
                .orElseThrow(() -> new MemberNotPayException("Member has not paid the membership fee for DNI: " + dni));

        //Comprobar si el pago esta dentro del plazo de validez, si no pasar a no activo
        if (!utilService.isPaymentValid(lastPayment.getEndDate())) {
            lastPayment.setActive(false);
            paymentRepository.save(lastPayment);
            throw new MemberNotPayException("Member has not paid the membership fee for DNI: " + dni);
        }

        //Si tiene el plan activo y es de un mes, pero la opcion de 3 dias a la semana, validar
        Plan plan = planRepository.findById(member.getPlan().getId())
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + member.getPlan().getId()));

        if(Constants.PLAN_TYPE_MONTHLY_THREE_DAYS.equals(plan.getCode())) {
            long countOfReceipts = receiptsRepository.countByMemberDniAndDateBetween(dni, utilService.getFirstDayOfTheWeek(), utilService.getLastDayOfTheWeek());
            if(countOfReceipts >= MAX_CHECKINS_PER_WEEK) {
                throw new WeeklyVisitLimitExceededException("Weekly visit limit exceeded for member with DNI: " + dni);
            }
        }

        //Registrar el check-in
        Receipts receipt = new Receipts();
        receipt.setMember(member);
        receipt.setDate(LocalDate.now());
        receiptsRepository.save(receipt);
    }
}