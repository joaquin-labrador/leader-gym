package com.leadergym.control.common.mapper;

import com.leadergym.control.dto.MemberResponseDto;
import com.leadergym.control.entity.Member;

public class MemberMapper {
    public static MemberResponseDto toMemberResponseDto(Member member) {
        return MemberResponseDto.builder()
                .dni(member.getDni())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .active(member.isActive())
                .planDescription(member.getPlan().getDescription())
                .expirationDate(member.getExpirationDate().toString())
                .build();
    }
}
