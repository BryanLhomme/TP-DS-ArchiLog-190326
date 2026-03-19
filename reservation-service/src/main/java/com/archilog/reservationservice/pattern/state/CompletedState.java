package com.archilog.reservationservice.pattern.state;

import com.archilog.reservationservice.exception.BusinessException;

public class CompletedState implements ReservationState {

    @Override
    public void cancel(ReservationContext context) {
        throw new BusinessException("Impossible d'annuler une réservation déjà complétée");
    }

    @Override
    public void complete(ReservationContext context) {
        throw new BusinessException("La réservation est déjà complétée");
    }

    @Override
    public String getStateName() {
        return "COMPLETED";
    }
}
