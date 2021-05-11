package com.epam.training.ticketservice.mappers;

import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Room;

public interface RoomEntityMapper {
    Room mapRoomEntity(RoomEntity roomEntity);
}
