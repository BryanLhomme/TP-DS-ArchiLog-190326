package com.archilog.memberservice.kafka;

import com.archilog.memberservice.event.MemberDeletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberEventProducer {

    private static final String TOPIC_MEMBER_DELETED = "member-deleted";

    private final KafkaTemplate<String, MemberDeletedEvent> kafkaTemplate;

    public MemberEventProducer(KafkaTemplate<String, MemberDeletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMemberDeleted(Long memberId) {
        kafkaTemplate.send(TOPIC_MEMBER_DELETED, memberId.toString(), new MemberDeletedEvent(memberId));
    }
}
