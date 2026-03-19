package com.archilog.reservationservice.event;

public class ReservationEvent {

    private Long reservationId;
    private Long memberId;
    private Long roomId;
    private String status;
    private long activeReservationCount;
    private int maxConcurrentBookings;

    public ReservationEvent() {}

    public ReservationEvent(Long reservationId, Long memberId, Long roomId, String status,
                            long activeReservationCount, int maxConcurrentBookings) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.roomId = roomId;
        this.status = status;
        this.activeReservationCount = activeReservationCount;
        this.maxConcurrentBookings = maxConcurrentBookings;
    }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getActiveReservationCount() { return activeReservationCount; }
    public void setActiveReservationCount(long activeReservationCount) { this.activeReservationCount = activeReservationCount; }

    public int getMaxConcurrentBookings() { return maxConcurrentBookings; }
    public void setMaxConcurrentBookings(int maxConcurrentBookings) { this.maxConcurrentBookings = maxConcurrentBookings; }
}
