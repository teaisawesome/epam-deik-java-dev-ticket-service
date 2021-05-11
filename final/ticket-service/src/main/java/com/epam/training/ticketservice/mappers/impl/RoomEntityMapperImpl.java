package com.epam.training.ticketservice.mappers.impl;

import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.mappers.RoomEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomEntityMapperImpl implements RoomEntityMapper {
    @Override
    public Room mapRoomEntity(RoomEntity roomEntity) {
        return new Room(roomEntity.getName(), roomEntity.getChairsRowsNumber(), roomEntity.getChairsColsNumber());
    }
}
