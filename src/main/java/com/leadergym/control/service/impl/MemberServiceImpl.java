package com.leadergym.control.service.impl;

import com.leadergym.control.common.mapper.MemberMapper;
import com.leadergym.control.dto.MemberCredentialsDTO;
import com.leadergym.control.dto.MemberResponseDTO;
import com.leadergym.control.dto.MemberUpdateCredentialsDTO;
import com.leadergym.control.entity.Member;
import com.leadergym.control.entity.Plan;
import com.leadergym.control.exception.MemberNotFoundException;
import com.leadergym.control.exception.PlanNotFoundException;
import com.leadergym.control.repository.MemberRepository;
import com.leadergym.control.repository.PlanRepository;
import com.leadergym.control.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                .orElseThrow(() -> new PlanNotFoundException("Plan not found with id: " + member.getPlanId()));
        Member newMember = new Member();
        newMember.setDni(member.getDni());
        newMember.setFirstName(member.getFirstName());
        newMember.setLastName(member.getLastName());
        newMember.setEmail(member.getEmail());
        newMember.setPhoneNumber(member.getPhoneNumber());
        newMember.setActive(true);
        newMember.setPlan(plan);
        newMember.setReceipts(null);
        newMember.setExpirationDate(getExpirationDate(plan.getDurationInDays()));
        newMember.setBirthDate(LocalDate.parse(member.getBirthDate()));
        memberRepository.save(newMember);
    }

    @Override
    public MemberResponseDTO getMemberByDni(String dni) {
        Member member = memberRepository.findByDni(dni);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }
        return MemberMapper.toMemberResponseDto(member);
    }

    @Override
    public void updateMember(String dni, MemberUpdateCredentialsDTO member) {
        Member existingMember = memberRepository.findByDni(dni);
        if (existingMember == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }
        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhoneNumber(member.getPhoneNumber());
        if (member.getPlanId() != null) {
            Plan plan = planRepository.findById(member.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan not found with id: " + member.getPlanId()));
            existingMember.setPlan(plan);
            existingMember.setExpirationDate(getExpirationDate(plan.getDurationInDays()));
        }
        memberRepository.save(existingMember);

    }

    @Override
    public void deleteMember(String dni) {
        Member existingMember = memberRepository.findByDni(dni);
        if (existingMember == null) {
            throw new MemberNotFoundException("Member not found with DNI: " + dni);
        }
        memberRepository.delete(existingMember);
    }

    @Override
    public Page<MemberResponseDTO> getPaginatedMembers(Pageable pageable) {

        Pageable finalPageable = pageable.getSort().isUnsorted()
                ? PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("lastName").ascending().and(Sort.by("firstName").ascending())
        )
                : pageable;

        return memberRepository.findAll(finalPageable)
                .map(MemberMapper::toMemberResponseDto);
    }


    private LocalDate getExpirationDate(Integer durationInDays) {
        return utilService.calculateExpiration(durationInDays);
    }


}

