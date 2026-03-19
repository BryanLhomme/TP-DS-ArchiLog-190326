package com.archilog.reservationservice.service;

import com.archilog.reservationservice.client.MemberClient;
import com.archilog.reservationservice.client.RoomClient;
import com.archilog.reservationservice.dto.CreateReservationDTO;
import com.archilog.reservationservice.exception.BusinessException;
import com.archilog.reservationservice.exception.ResourceNotFoundException;
import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.model.ReservationStatus;
import com.archilog.reservationservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomClient roomClient;
    private final MemberClient memberClient;

    public ReservationService(ReservationRepository reservationRepository,
                              RoomClient roomClient,
                              MemberClient memberClient) {
        this.reservationRepository = reservationRepository;
        this.roomClient = roomClient;
        this.memberClient = memberClient;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable avec l'id : " + id));
    }

    public List<Reservation> getReservationsByMemberId(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    public List<Reservation> getReservationsByRoomId(Long roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    @Transactional
    public Reservation createReservation(CreateReservationDTO dto) {
        // Vérifier que la salle existe et est disponible (appel REST → Room Service)
        if (!roomClient.isRoomAvailable(dto.roomId())) {
            throw new BusinessException("La salle " + dto.roomId() + " n'est pas disponible");
        }

        // Vérifier que le membre existe et n'est pas suspendu (appel REST → Member Service)
        if (memberClient.isMemberSuspended(dto.memberId())) {
            throw new BusinessException("Le membre " + dto.memberId() + " est suspendu et ne peut pas réserver");
        }

        // Vérifier qu'il n'y a pas de chevauchement de créneau sur cette salle
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(
                dto.roomId(), dto.startDateTime(), dto.endDateTime());
        if (!overlapping.isEmpty()) {
            throw new BusinessException("La salle " + dto.roomId() + " est déjà réservée sur ce créneau");
        }

        // Vérifier le quota du membre
        int maxBookings = memberClient.getMemberMaxBookings(dto.memberId());
        long activeCount = reservationRepository.countByMemberIdAndStatus(dto.memberId(), ReservationStatus.CONFIRMED);
        if (activeCount >= maxBookings) {
            throw new BusinessException("Le membre " + dto.memberId() + " a atteint son quota maximum de réservations (" + maxBookings + ")");
        }

        // Créer la réservation avec statut CONFIRMED
        Reservation reservation = new Reservation();
        reservation.setRoomId(dto.roomId());
        reservation.setMemberId(dto.memberId());
        reservation.setStartDateTime(dto.startDateTime());
        reservation.setEndDateTime(dto.endDateTime());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Reservation saved = reservationRepository.save(reservation);

        // Marquer la salle comme indisponible
        roomClient.updateRoomAvailability(dto.roomId(), false);

        return saved;
    }

    @Transactional
    public Reservation cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new BusinessException("Seule une réservation CONFIRMED peut être annulée");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation saved = reservationRepository.save(reservation);

        // Rendre la salle disponible si plus aucune réservation CONFIRMED sur cette salle
        List<Reservation> remaining = reservationRepository.findByRoomIdAndStatus(
                reservation.getRoomId(), ReservationStatus.CONFIRMED);
        if (remaining.isEmpty()) {
            roomClient.updateRoomAvailability(reservation.getRoomId(), true);
        }

        return saved;
    }

    @Transactional
    public Reservation completeReservation(Long id) {
        Reservation reservation = getReservationById(id);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new BusinessException("Seule une réservation CONFIRMED peut être complétée");
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        Reservation saved = reservationRepository.save(reservation);

        // Rendre la salle disponible si plus aucune réservation CONFIRMED sur cette salle
        List<Reservation> remaining = reservationRepository.findByRoomIdAndStatus(
                reservation.getRoomId(), ReservationStatus.CONFIRMED);
        if (remaining.isEmpty()) {
            roomClient.updateRoomAvailability(reservation.getRoomId(), true);
        }

        return saved;
    }
}
