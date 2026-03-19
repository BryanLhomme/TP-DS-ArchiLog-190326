package com.archilog.reservationservice.client;

import com.archilog.reservationservice.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RoomClient {

    private static final String ROOM_SERVICE_URL = "http://room-service/api/rooms";

    private final RestTemplate restTemplate;

    public RoomClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getRoomById(Long roomId) {
        try {
            return restTemplate.getForObject(ROOM_SERVICE_URL + "/" + roomId, Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new BusinessException("Salle introuvable avec l'id : " + roomId);
        }
    }

    public boolean isRoomAvailable(Long roomId) {
        Map<String, Object> room = getRoomById(roomId);
        return Boolean.TRUE.equals(room.get("available"));
    }

    public void updateRoomAvailability(Long roomId, boolean available) {
        restTemplate.put(ROOM_SERVICE_URL + "/" + roomId + "/availability?available=" + available, null);
    }
}
