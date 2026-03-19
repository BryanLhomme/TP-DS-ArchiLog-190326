package com.archilog.reservationservice.pattern.state;

import com.archilog.reservationservice.model.Reservation;

public interface ReservationState {

    void cancel(ReservationContext context);

    void complete(ReservationContext context);

    String getStateName();
}
