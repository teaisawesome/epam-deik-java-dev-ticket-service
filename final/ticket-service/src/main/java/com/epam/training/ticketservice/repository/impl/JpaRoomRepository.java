package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaRoomRepository implements RoomRepository {
    private RoomDao roomDao;

    @Autowired
    public JpaRoomRepository(RoomDao roomDao) {
        this.roomDao = roomDao;
    }


    @Override
    public boolean saveRoomToDatabase(RoomEntity roomEntity) {
        if (roomDao.existsByName(roomEntity.getName())) {
            return false;
        }

        roomDao.save(roomEntity);

        return true;
    }

    @Override
    public boolean deleteRoomFromDatabase(String name) {
        if (!roomDao.existsByName(name)) {
            return false;
        }

        roomDao.deleteByName(name);

        return true;
    }

    @Override
    public List<RoomEntity> findAllRooms() {
        return roomDao.findAll();
    }

    @Override
    public boolean updateRoomFromDatabase(RoomEntity roomEntity) {
        String roomName = roomEntity.getName();

        if (!roomDao.existsByName(roomName)) {
            return false;
        }

        RoomEntity updatedRoomEntity = roomDao.getRoomEntityByName(roomName);
        updatedRoomEntity.setChairsRowsNumber(roomEntity.getChairsRowsNumber());
        updatedRoomEntity.setChairsColsNumber(roomEntity.getChairsColsNumber());

        roomDao.save(updatedRoomEntity);

        return true;
    }
}
