package com.archilog.reservationservice.kafka;

import com.archilog.reservationservice.event.RoomDeletedEvent;
import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.model.ReservationStatus;
import com.archilog.reservationservice.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(RoomEventConsumer.class);

    private final ReservationRepository reservationRepository;

    public RoomEventConsumer(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @KafkaListener(topics = "room-deleted", groupId = "reservation-service-group")
    public void handleRoomDeleted(RoomDeletedEvent event) {
        log.info("Salle supprimée reçue : roomId={}", event.getRoomId());

        // Annuler toutes les réservations CONFIRMED de cette salle
        List<Reservation> reservations = reservationRepository.findByRoomIdAndStatus(
                event.getRoomId(), ReservationStatus.CONFIRMED);

        for (Reservation reservation : reservations) {
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
            log.info("Réservation {} annulée suite à la suppression de la salle {}",
                    reservation.getId(), event.getRoomId());
        }
    }
}
