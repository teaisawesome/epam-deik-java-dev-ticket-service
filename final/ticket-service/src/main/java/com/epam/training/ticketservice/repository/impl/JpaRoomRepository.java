package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.mappers.RoomEntityMapper;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaRoomRepository implements RoomRepository {
    private RoomDao roomDao;
    private final RoomEntityMapper roomEntityMapper;

    @Autowired
    public JpaRoomRepository(RoomDao roomDao, RoomEntityMapper roomEntityMapper) {
        this.roomDao = roomDao;
        this.roomEntityMapper = roomEntityMapper;
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

    @Override
    public Room getRoomByName(String name) throws RoomNotFoundException {
        if (!roomDao.existsByName(name)) {
            throw new RoomNotFoundException("Room Not Found!");
        }

        return roomEntityMapper.mapRoomEntity(roomDao.getRoomEntityByName(name));
    }

    @Override
    public RoomEntity getRoomEntityByName(String name) {
        return roomDao.getRoomEntityByName(name);
    }
}