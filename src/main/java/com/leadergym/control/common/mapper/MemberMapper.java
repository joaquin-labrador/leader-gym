package com.leadergym.control.common.mapper;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.dto.MemberResponseDTO;
import com.leadergym.control.entity.Member;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MemberMapper {
    public static MemberResponseDTO toMemberResponseDto(@NotNull Member member) {
        LocalDate today = LocalDate.now(Constants.ARGENTINA_TIME_ZONE);
        return MemberResponseDTO.builder()
                .dni(member.getDni())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .active((today.isBefore(member.getExpirationDate()) || today.isEqual(member.getExpirationDate())) && member.isActive()  )
                .planDescription(member.getPlan().getCode())
                .planId(member.getPlan().getId())
                .expirationDate(member.getExpirationDate().toString())
                .age(MapperUtils.calculateAge(member.getBirthDate()))
                .build();
    }
}
