package com.archilog.reservationservice.event;

public class RoomDeletedEvent {

    private Long roomId;

    public RoomDeletedEvent() {}

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
}
