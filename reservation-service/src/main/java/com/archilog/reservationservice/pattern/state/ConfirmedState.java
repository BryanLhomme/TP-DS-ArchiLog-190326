package com.archilog.reservationservice.pattern.state;

import com.archilog.reservationservice.model.ReservationStatus;

public class ConfirmedState implements ReservationState {

    @Override
    public void cancel(ReservationContext context) {
        context.getReservation().setStatus(ReservationStatus.CANCELLED);
        context.setState(new CancelledState());
    }

    @Override
    public void complete(ReservationContext context) {
        context.getReservation().setStatus(ReservationStatus.COMPLETED);
        context.setState(new CompletedState());
    }

    @Override
    public String getStateName() {
        return "CONFIRMED";
    }
}
