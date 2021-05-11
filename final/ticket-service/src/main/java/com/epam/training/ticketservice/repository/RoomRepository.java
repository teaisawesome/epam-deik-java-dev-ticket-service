package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;

import java.util.List;

public interface RoomRepository {
    boolean saveRoomToDatabase(RoomEntity roomEntity);

    boolean deleteRoomFromDatabase(String name);

    List<RoomEntity> findAllRooms();

    boolean updateRoomFromDatabase(RoomEntity roomEntity);

    Room getRoomByName(String name) throws RoomNotFoundException;

    RoomEntity getRoomEntityByName(String name);
}
