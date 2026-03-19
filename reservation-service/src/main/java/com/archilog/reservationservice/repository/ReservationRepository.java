package com.archilog.reservationservice.repository;

import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMemberIdAndStatus(Long memberId, ReservationStatus status);

    List<Reservation> findByRoomIdAndStatus(Long roomId, ReservationStatus status);

    List<Reservation> findByMemberId(Long memberId);

    List<Reservation> findByRoomId(Long roomId);

    @Query("SELECT r FROM Reservation r WHERE r.roomId = :roomId AND r.status = 'CONFIRMED' " +
           "AND r.startDateTime < :endDateTime AND r.endDateTime > :startDateTime")
    List<Reservation> findOverlappingReservations(
            @Param("roomId") Long roomId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    long countByMemberIdAndStatus(Long memberId, ReservationStatus status);
}
