package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScreeningRepository {
    void createScreening(ScreeningEntity screeningEntity);

    List<ScreeningEntity> findAllScreening();

    boolean isScreeningExistsByRoom(RoomEntity roomEntity);

    List<ScreeningEntity> findAllScreeningByRoom(RoomEntity roomEntity);

    boolean isScreeningExistsByMovieAndRoomAndStartTime(MovieEntity movieEntity, RoomEntity roomEntity, Date startTime);

    boolean deleteScreening(MovieEntity movieEntity, RoomEntity roomEntity, Date startTime);
}
