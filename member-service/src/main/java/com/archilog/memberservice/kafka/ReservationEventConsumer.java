package com.archilog.memberservice.kafka;

import com.archilog.memberservice.event.ReservationEvent;
import com.archilog.memberservice.model.Member;
import com.archilog.memberservice.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ReservationEventConsumer.class);

    private final MemberRepository memberRepository;

    public ReservationEventConsumer(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @KafkaListener(topics = "reservation-created", groupId = "member-service-group")
    public void handleReservationCreated(ReservationEvent event) {
        log.info("Réservation créée reçue pour le membre {} — actives: {}, max: {}",
                event.getMemberId(), event.getActiveReservationCount(), event.getMaxConcurrentBookings());

        // Si le membre atteint son quota → le suspendre
        if (event.getActiveReservationCount() >= event.getMaxConcurrentBookings()) {
            memberRepository.findById(event.getMemberId()).ifPresent(member -> {
                member.setSuspended(true);
                memberRepository.save(member);
                log.info("Membre {} suspendu (quota atteint: {}/{})",
                        member.getId(), event.getActiveReservationCount(), event.getMaxConcurrentBookings());
            });
        }
    }

    @KafkaListener(topics = "reservation-status-changed", groupId = "member-service-group")
    public void handleReservationStatusChanged(ReservationEvent event) {
        log.info("Changement de statut reçu pour le membre {} — statut: {}, actives: {}, max: {}",
                event.getMemberId(), event.getStatus(), event.getActiveReservationCount(), event.getMaxConcurrentBookings());

        // Si le membre repasse en dessous du quota → le désuspendre
        if (event.getActiveReservationCount() < event.getMaxConcurrentBookings()) {
            memberRepository.findById(event.getMemberId()).ifPresent(member -> {
                if (member.isSuspended()) {
                    member.setSuspended(false);
                    memberRepository.save(member);
                    log.info("Membre {} désuspendu (actives: {}/{})",
                            member.getId(), event.getActiveReservationCount(), event.getMaxConcurrentBookings());
                }
            });
        }
    }
}
