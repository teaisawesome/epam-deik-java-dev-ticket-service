package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RoomDao extends JpaRepository<RoomEntity, UUID> {
    boolean existsByName(String name);

    @Transactional
    void deleteByName(String name);

    RoomEntity getRoomEntityByName(String name);
}
