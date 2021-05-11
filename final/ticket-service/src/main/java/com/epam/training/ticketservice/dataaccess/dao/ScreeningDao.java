package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ScreeningDao extends JpaRepository<ScreeningEntity, UUID> {
    boolean existsScreeningEntityByRoom(RoomEntity roomEntity);

    List<ScreeningEntity> findAllByRoom(RoomEntity roomEntity);

    boolean existsByMovieAndRoomAndStartTime(MovieEntity movieEntity, RoomEntity roomEntity, Date startTime);

    @Transactional
    void deleteByMovieAndRoomAndStartTime(MovieEntity movieEntity, RoomEntity roomEntity, Date startTime);
}
