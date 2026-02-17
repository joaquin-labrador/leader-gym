package com.leadergym.control.service.impl;

import com.leadergym.control.common.enums.PlanType;
import com.leadergym.control.dto.MemberCredentialsDTO;
import com.leadergym.control.entity.Member;
import com.leadergym.control.entity.Plan;
import com.leadergym.control.repository.MemberRepository;
import com.leadergym.control.repository.PlanRepository;
import com.leadergym.control.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UtilService utilService;


    @Override
    public void createMember(MemberCredentialsDTO member) {
        Plan plan = planRepository.findById(member.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + member.getPlanId()));
        Member newMember = new Member();
        newMember.setDni(member.getDni());
        newMember.setFirstName(member.getFirstName());
        newMember.setLastName(member.getLastName());
        newMember.setEmail(member.getEmail());
        newMember.setPhoneNumber(member.getPhoneNumber());
        newMember.setActive(true);
        newMember.setPlan(plan);
        newMember.setReceipts(null);
        newMember.setExpirationDate(getExpirationDate(plan.getDescription()));
        memberRepository.save(newMember);
    }

    @Override
    public void getMemberByDni(String dni) {

    }

    @Override
    public void updateMember(String dni, MemberCredentialsDTO member) {

    }

    @Override
    public void deleteMember(String dni) {

    }

    private LocalDate getExpirationDate(String planName) {
        PlanType planType = PlanType.valueOf(planName.toUpperCase());
        int durationInDays = planType.getDurationInDays();
        return utilService.calculateExpiration(durationInDays);
    }
}

