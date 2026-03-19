package com.archilog.memberservice.controller;

import com.archilog.memberservice.dto.CreateMemberDTO;
import com.archilog.memberservice.model.Member;
import com.archilog.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Members", description = "Gestion des membres et abonnements")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les membres")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un membre par son id")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    @Operation(summary = "Inscrire un nouveau membre")
    public ResponseEntity<Member> createMember(@RequestBody CreateMemberDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un membre")
    public Member updateMember(@PathVariable Long id, @RequestBody CreateMemberDTO dto) {
        return memberService.updateMember(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un membre (déclenche Kafka pour supprimer ses réservations)")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/suspend")
    @Operation(summary = "Suspendre ou désuspendre un membre")
    public Member updateSuspension(@PathVariable Long id, @RequestParam boolean suspended) {
        return memberService.updateSuspension(id, suspended);
    }
}
