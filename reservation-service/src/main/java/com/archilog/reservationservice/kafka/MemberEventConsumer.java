package com.archilog.reservationservice.kafka;

import com.archilog.reservationservice.event.MemberDeletedEvent;
import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(MemberEventConsumer.class);

    private final ReservationRepository reservationRepository;

    public MemberEventConsumer(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @KafkaListener(topics = "member-deleted", groupId = "reservation-service-group")
    public void handleMemberDeleted(MemberDeletedEvent event) {
        log.info("Membre supprimé reçu : memberId={}", event.getMemberId());

        // Supprimer toutes les réservations de ce membre
        List<Reservation> reservations = reservationRepository.findByMemberId(event.getMemberId());

        reservationRepository.deleteAll(reservations);
        log.info("{} réservation(s) supprimée(s) suite à la suppression du membre {}",
                reservations.size(), event.getMemberId());
    }
}
