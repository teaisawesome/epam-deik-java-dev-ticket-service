package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {

    private final ScreeningDao screeningDao;

    public JpaScreeningRepository(ScreeningDao screeningDao) {
        this.screeningDao = screeningDao;
    }

    @Override
    public void createScreening(ScreeningEntity screeningEntity) {
        screeningDao.save(screeningEntity);
    }

    @Override
    public List<ScreeningEntity> findAllScreening() {
        return screeningDao.findAll();
    }

    @Override
    public boolean isScreeningExistsByRoom(RoomEntity roomEntity) {
        return screeningDao.existsScreeningEntityByRoom(roomEntity);
    }

    @Override
    public List<ScreeningEntity> findAllScreeningByRoom(RoomEntity roomEntity) {
        return screeningDao.findAllByRoom(roomEntity);
    }

    @Override
    public boolean isScreeningExistsByMovieAndRoomAndStartTime(MovieEntity movieEntity, RoomEntity roomEntity, Date startTime) {
        return screeningDao.existsByMovieAndRoomAndStartTime(movieEntity, roomEntity, startTime);
    }

    @Override
    public boolean deleteScreening(MovieEntity movieEntity, RoomEntity roomEntity, Date startTime) {
        if (!screeningDao.existsByMovieAndRoomAndStartTime(movieEntity, roomEntity, startTime)) {
            return false;
        }

        screeningDao.deleteByMovieAndRoomAndStartTime(movieEntity, roomEntity, startTime);

        return true;
    }
}
