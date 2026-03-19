package com.archilog.reservationservice.pattern.state;

import com.archilog.reservationservice.model.Reservation;
import com.archilog.reservationservice.model.ReservationStatus;

public class ReservationContext {

    private Reservation reservation;
    private ReservationState state;

    public ReservationContext(Reservation reservation) {
        this.reservation = reservation;
        this.state = resolveState(reservation.getStatus());
    }

    private ReservationState resolveState(ReservationStatus status) {
        return switch (status) {
            case CONFIRMED -> new ConfirmedState();
            case CANCELLED -> new CancelledState();
            case COMPLETED -> new CompletedState();
        };
    }

    public void cancel() {
        state.cancel(this);
    }

    public void complete() {
        state.complete(this);
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }

    public String getCurrentStateName() {
        return state.getStateName();
    }
}
