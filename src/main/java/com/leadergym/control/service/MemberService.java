package com.leadergym.control.service;

import com.leadergym.control.dto.MemberCredentialsDTO;
import com.leadergym.control.dto.MemberResponseDto;
import com.leadergym.control.dto.MemberUpdateCredentialsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    void createMember(MemberCredentialsDTO member);

    MemberResponseDto getMemberByDni(String dni);

    void updateMember(String dni, MemberUpdateCredentialsDTO member);

    void deleteMember(String dni);

    Page<MemberResponseDto> getPaginatedMembers(Pageable pageable);
}
