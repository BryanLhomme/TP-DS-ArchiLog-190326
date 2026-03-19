package com.archilog.reservationservice.kafka;

import com.archilog.reservationservice.event.ReservationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventProducer {

    private static final String TOPIC_RESERVATION_CREATED = "reservation-created";
    private static final String TOPIC_RESERVATION_STATUS_CHANGED = "reservation-status-changed";

    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    public ReservationEventProducer(KafkaTemplate<String, ReservationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishReservationCreated(ReservationEvent event) {
        kafkaTemplate.send(TOPIC_RESERVATION_CREATED, event.getMemberId().toString(), event);
    }

    public void publishReservationStatusChanged(ReservationEvent event) {
        kafkaTemplate.send(TOPIC_RESERVATION_STATUS_CHANGED, event.getMemberId().toString(), event);
    }
}
