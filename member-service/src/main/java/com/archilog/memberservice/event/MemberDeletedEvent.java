package com.archilog.memberservice.event;

public class MemberDeletedEvent {

    private Long memberId;

    public MemberDeletedEvent() {}

    public MemberDeletedEvent(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
}
