package com.archilog.roomservice.service;

import com.archilog.roomservice.dto.CreateRoomDTO;
import com.archilog.roomservice.exception.ResourceNotFoundException;
import com.archilog.roomservice.kafka.RoomEventProducer;
import com.archilog.roomservice.model.Room;
import com.archilog.roomservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomEventProducer roomEventProducer;

    public RoomService(RoomRepository roomRepository, RoomEventProducer roomEventProducer) {
        this.roomRepository = roomRepository;
        this.roomEventProducer = roomEventProducer;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle introuvable avec l'id : " + id));
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }

    public List<Room> getRoomsByCity(String city) {
        return roomRepository.findByCity(city);
    }

    public Room createRoom(CreateRoomDTO dto) {
        Room room = new Room();
        room.setName(dto.name());
        room.setCity(dto.city());
        room.setCapacity(dto.capacity());
        room.setType(dto.type());
        room.setHourlyRate(dto.hourlyRate());
        room.setAvailable(true);
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, CreateRoomDTO dto) {
        Room room = getRoomById(id);
        room.setName(dto.name());
        room.setCity(dto.city());
        room.setCapacity(dto.capacity());
        room.setType(dto.type());
        room.setHourlyRate(dto.hourlyRate());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
        // Publier l'événement Kafka pour annuler les réservations associées
        roomEventProducer.publishRoomDeleted(id);
    }

    public Room updateAvailability(Long id, boolean available) {
        Room room = getRoomById(id);
        room.setAvailable(available);
        return roomRepository.save(room);
    }
}
