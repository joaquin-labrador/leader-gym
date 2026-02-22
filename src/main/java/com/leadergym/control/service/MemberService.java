package com.leadergym.control.service;

import com.leadergym.control.dto.MemberCredentialsDTO;
import com.leadergym.control.dto.MemberResponseDTO;
import com.leadergym.control.dto.MemberUpdateCredentialsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    void createMember(MemberCredentialsDTO member);

    MemberResponseDTO getMemberByDni(String dni);

    void updateMember(String dni, MemberUpdateCredentialsDTO member);

    void deleteMember(String dni);

    Page<MemberResponseDTO> getPaginatedMembers(Pageable pageable);
}
