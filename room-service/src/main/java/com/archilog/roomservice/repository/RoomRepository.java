package com.archilog.roomservice.repository;

import com.archilog.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByAvailableTrue();

    List<Room> findByCity(String city);
}
