package com.archilog.memberservice.service;

import com.archilog.memberservice.dto.CreateMemberDTO;
import com.archilog.memberservice.exception.ResourceNotFoundException;
import com.archilog.memberservice.kafka.MemberEventProducer;
import com.archilog.memberservice.model.Member;
import com.archilog.memberservice.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberEventProducer memberEventProducer;

    public MemberService(MemberRepository memberRepository, MemberEventProducer memberEventProducer) {
        this.memberRepository = memberRepository;
        this.memberEventProducer = memberEventProducer;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membre introuvable avec l'id : " + id));
    }

    public Member createMember(CreateMemberDTO dto) {
        Member member = new Member();
        member.setFullName(dto.fullName());
        member.setEmail(dto.email());
        member.setSubscriptionType(dto.subscriptionType());
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, CreateMemberDTO dto) {
        Member member = getMemberById(id);
        member.setFullName(dto.fullName());
        member.setEmail(dto.email());
        member.setSubscriptionType(dto.subscriptionType());
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
        // Publier l'événement Kafka pour supprimer les réservations associées
        memberEventProducer.publishMemberDeleted(id);
    }

    public Member updateSuspension(Long id, boolean suspended) {
        Member member = getMemberById(id);
        member.setSuspended(suspended);
        return memberRepository.save(member);
    }
}
