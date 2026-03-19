package com.archilog.reservationservice.pattern.state;

import com.archilog.reservationservice.exception.BusinessException;

public class CancelledState implements ReservationState {

    @Override
    public void cancel(ReservationContext context) {
        throw new BusinessException("La réservation est déjà annulée");
    }

    @Override
    public void complete(ReservationContext context) {
        throw new BusinessException("Impossible de compléter une réservation annulée");
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}
