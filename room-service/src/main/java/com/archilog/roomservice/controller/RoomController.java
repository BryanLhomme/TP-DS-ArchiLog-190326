package com.archilog.roomservice.controller;

import com.archilog.roomservice.dto.CreateRoomDTO;
import com.archilog.roomservice.model.Room;
import com.archilog.roomservice.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms", description = "Gestion des salles de coworking")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les salles")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une salle par son id")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping("/available")
    @Operation(summary = "Lister les salles disponibles")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Lister les salles par ville")
    public List<Room> getRoomsByCity(@PathVariable String city) {
        return roomService.getRoomsByCity(city);
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle salle")
    public ResponseEntity<Room> createRoom(@RequestBody CreateRoomDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une salle")
    public Room updateRoom(@PathVariable Long id, @RequestBody CreateRoomDTO dto) {
        return roomService.updateRoom(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une salle (déclenche Kafka pour annuler ses réservations)")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/availability")
    @Operation(summary = "Changer la disponibilité d'une salle")
    public Room updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
        return roomService.updateAvailability(id, available);
    }
}
