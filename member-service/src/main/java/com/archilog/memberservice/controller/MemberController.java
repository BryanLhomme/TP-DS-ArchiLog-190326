package com.archilog.memberservice.controller;

import com.archilog.memberservice.dto.CreateMemberDTO;
import com.archilog.memberservice.model.Member;
import com.archilog.memberservice.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody CreateMemberDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(dto));
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody CreateMemberDTO dto) {
        return memberService.updateMember(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/suspend")
    public Member updateSuspension(@PathVariable Long id, @RequestParam boolean suspended) {
        return memberService.updateSuspension(id, suspended);
    }
}
