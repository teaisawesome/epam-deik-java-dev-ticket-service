package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.mappers.RoomEntityMapper;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomEntityMapper roomEntityMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomEntityMapper roomEntityMapper) {
        this.roomRepository = roomRepository;
        this.roomEntityMapper = roomEntityMapper;
    }

    @Override
    public boolean createRoom(String name, int chairsRowsNumber, int chairsColsNumber) {
        RoomEntity roomEntity = new RoomEntity(name, chairsRowsNumber, chairsColsNumber);

        return roomRepository.saveRoomToDatabase(roomEntity);
    }

    @Override
    public boolean deleteRoom(String name) {
        return roomRepository.deleteRoomFromDatabase(name);
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAllRooms()
                .stream()
                .map(roomEntity -> roomEntityMapper.mapRoomEntity(roomEntity))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateRoom(String name, int chairsRowsNumber, int chairsColsNumber) {
        RoomEntity roomEntity = new RoomEntity(name, chairsRowsNumber, chairsColsNumber);
        return roomRepository.updateRoomFromDatabase(roomEntity);
    }
}
