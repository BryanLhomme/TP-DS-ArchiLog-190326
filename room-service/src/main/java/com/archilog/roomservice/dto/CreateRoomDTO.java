package com.archilog.roomservice.dto;

import com.archilog.roomservice.model.RoomType;
import java.math.BigDecimal;

public record CreateRoomDTO(
        String name,
        String city,
        Integer capacity,
        RoomType type,
        BigDecimal hourlyRate
) {}
