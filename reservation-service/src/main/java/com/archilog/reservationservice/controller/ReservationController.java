package com.archilog.reservationservice.controller;

import com.archilog.reservationservice.dto.CreateReservationDTO;
import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Reservation> getByMemberId(@PathVariable Long memberId) {
        return reservationService.getReservationsByMemberId(memberId);
    }

    @GetMapping("/room/{roomId}")
    public List<Reservation> getByRoomId(@PathVariable Long roomId) {
        return reservationService.getReservationsByRoomId(roomId);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(dto));
    }

    @PutMapping("/{id}/cancel")
    public Reservation cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @PutMapping("/{id}/complete")
    public Reservation completeReservation(@PathVariable Long id) {
        return reservationService.completeReservation(id);
    }
}
