package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;

import java.util.List;

public interface RoomRepository {
    boolean saveRoomToDatabase(RoomEntity roomEntity);

    boolean deleteRoomFromDatabase(String name);

    List<RoomEntity> findAllRooms();

    boolean updateRoomFromDatabase(RoomEntity roomEntity);

    RoomEntity getRoomEntityByName(String name) throws RoomNotFoundException;
}
