package com.archilog.reservationservice.controller;

import com.archilog.reservationservice.dto.CreateReservationDTO;
import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Gestion des réservations de salles")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les réservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une réservation par son id")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Lister les réservations d'un membre")
    public List<Reservation> getByMemberId(@PathVariable Long memberId) {
        return reservationService.getReservationsByMemberId(memberId);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Lister les réservations d'une salle")
    public List<Reservation> getByRoomId(@PathVariable Long roomId) {
        return reservationService.getReservationsByRoomId(roomId);
    }

    @PostMapping
    @Operation(summary = "Créer une réservation (vérifie dispo salle + suspension membre + quota)")
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(dto));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Annuler une réservation (State Pattern + Kafka désuspension)")
    public Reservation cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Marquer une réservation comme terminée (State Pattern + Kafka désuspension)")
    public Reservation completeReservation(@PathVariable Long id) {
        return reservationService.completeReservation(id);
    }
}
