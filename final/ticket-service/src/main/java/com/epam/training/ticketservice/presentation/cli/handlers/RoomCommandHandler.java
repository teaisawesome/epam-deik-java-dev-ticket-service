package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.presentation.cli.helpers.ListToStringConverter;
import com.epam.training.ticketservice.service.LoginService;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class RoomCommandHandler extends AbstractAuthenticatedCommand {

    private RoomService roomService;

    private ListToStringConverter listToStringConverter;

    public RoomCommandHandler(LoginService loginService,
                              RoomService roomService,
                              ListToStringConverter listToStringConverter) {
        super(loginService);
        this.roomService = roomService;
        this.listToStringConverter = listToStringConverter;
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Create new room", key = "create room")
    public String createRoom(String roomName, int roomChairsRowsNumber, int roomChairsColsNumber) {
        return roomService.createRoom(roomName, roomChairsRowsNumber, roomChairsColsNumber)
                ? "Room Created!"
                : "Room Creation Failed! Room with given room name already exists!";
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public String listAllRooms() {
        List<Room> rooms = roomService.listRooms();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }

        return listToStringConverter.convertRooms(rooms);
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Update room from rooms", key = "update room")
    public String updateMovie(String roomName, int roomChairsRowsNumber, int roomChairsColsNumber) {
        return roomService.updateRoom(roomName, roomChairsRowsNumber, roomChairsColsNumber)
                ? "Room Updated!"
                : "Room Update Failed! Room with given room name not exists!";
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Delete room from rooms", key = "delete room")
    public String deleteMovie(String roomName) {
        return roomService.deleteRoom(roomName)
                ? "Room Deleted!"
                : "Room Deletion Failed! Room with given room name not exists!";
    }
}
