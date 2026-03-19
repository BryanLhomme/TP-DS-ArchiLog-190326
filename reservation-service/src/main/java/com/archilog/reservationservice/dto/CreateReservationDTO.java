package com.archilog.reservationservice.dto;

import java.time.LocalDateTime;

public record CreateReservationDTO(
        Long roomId,
        Long memberId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {}
