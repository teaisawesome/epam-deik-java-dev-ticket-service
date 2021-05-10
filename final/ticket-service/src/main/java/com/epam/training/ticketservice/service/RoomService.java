package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.user.Room;

import java.util.List;

public interface RoomService {
    boolean createRoom(String name, int chairsRowsNumber, int chairsColsNumber);

    boolean deleteRoom(String name);

    List<Room> listRooms();

    boolean updateRoom(String name, int chairsRowsNumber, int chairsColsNumber);
}
