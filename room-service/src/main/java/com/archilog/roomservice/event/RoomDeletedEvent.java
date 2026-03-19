package com.archilog.roomservice.event;

public class RoomDeletedEvent {

    private Long roomId;

    public RoomDeletedEvent() {}

    public RoomDeletedEvent(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
}
