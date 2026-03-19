package com.archilog.roomservice.controller;

import com.archilog.roomservice.dto.CreateRoomDTO;
import com.archilog.roomservice.model.Room;
import com.archilog.roomservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/city/{city}")
    public List<Room> getRoomsByCity(@PathVariable String city) {
        return roomService.getRoomsByCity(city);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody CreateRoomDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(dto));
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody CreateRoomDTO dto) {
        return roomService.updateRoom(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/availability")
    public Room updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
        return roomService.updateAvailability(id, available);
    }
}
