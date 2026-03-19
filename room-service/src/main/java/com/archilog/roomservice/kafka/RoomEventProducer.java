package com.archilog.roomservice.kafka;

import com.archilog.roomservice.event.RoomDeletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoomEventProducer {

    private static final String TOPIC_ROOM_DELETED = "room-deleted";

    private final KafkaTemplate<String, RoomDeletedEvent> kafkaTemplate;

    public RoomEventProducer(KafkaTemplate<String, RoomDeletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishRoomDeleted(Long roomId) {
        kafkaTemplate.send(TOPIC_ROOM_DELETED, roomId.toString(), new RoomDeletedEvent(roomId));
    }
}
