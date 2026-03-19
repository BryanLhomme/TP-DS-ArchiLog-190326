package com.archilog.reservationservice.event;

public class MemberDeletedEvent {

    private Long memberId;

    public MemberDeletedEvent() {}

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
}
