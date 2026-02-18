package com.leadergym.control.controller;

import com.leadergym.control.dto.MemberCredentialsDTO;
import com.leadergym.control.dto.MemberResponseDto;
import com.leadergym.control.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberCredentialsDTO member) {
        memberService.createMember(member);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Void> updateMember(@Valid @PathVariable String dni, @RequestBody MemberCredentialsDTO member) {
        memberService.updateMember(dni, member);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> deleteMember(@PathVariable String dni) {
        memberService.deleteMember(dni);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{dni}")
    public ResponseEntity<MemberResponseDto> getMemberByDni(@PathVariable String dni) {
        return ResponseEntity.ok(memberService.getMemberByDni(dni));
    }

    @GetMapping
    public ResponseEntity<Page<MemberResponseDto>> getMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getPaginatedMembers(pageable));
    }
}
