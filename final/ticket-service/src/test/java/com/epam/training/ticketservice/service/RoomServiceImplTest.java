package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.mappers.RoomEntityMapper;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceImplTest {
    private RoomRepository roomRepository;
    private RoomEntityMapper roomEntityMapper;

    private RoomServiceImpl underTest;

    private final String ROOMNAME_LONGISLAND = "LongIsland";
    private final int CHAIRSROWSNUMBER_LONGISLAND = 10;
    private final int CHAIRSCOLSNUMBER_LONGISLAND = 10;
    private final RoomEntity ROOM_ENTITY_LONGISLAND = new RoomEntity(ROOMNAME_LONGISLAND,
            CHAIRSROWSNUMBER_LONGISLAND,
            CHAIRSCOLSNUMBER_LONGISLAND);
    private final Room ROOM_LONGISLAND = new Room(ROOMNAME_LONGISLAND,
            CHAIRSROWSNUMBER_LONGISLAND,
            CHAIRSCOLSNUMBER_LONGISLAND);

    private final String ROOMNAME_HOLLYWOOD = "Hollywood";
    private final int CHAIRSROWSNUMBER_HOLLYWOOD = 8;
    private final int CHAIRSCOLSNUMBER_HOLLYWOOD = 5;
    private final RoomEntity ROOM_ENTITY_HOLLYWOOD = new RoomEntity(ROOMNAME_HOLLYWOOD,
            CHAIRSROWSNUMBER_HOLLYWOOD,
            CHAIRSCOLSNUMBER_HOLLYWOOD);
    private final Room ROOM_HOLLYWOOD = new Room(ROOMNAME_HOLLYWOOD,
            CHAIRSROWSNUMBER_HOLLYWOOD,
            CHAIRSCOLSNUMBER_HOLLYWOOD);


    @BeforeEach
    public void init() {
        roomRepository = Mockito.mock(RoomRepository.class);
        roomEntityMapper = Mockito.mock(RoomEntityMapper.class);
        underTest = new RoomServiceImpl(roomRepository, roomEntityMapper);
    }

    @Test
    public void testCreateRoomShouldSaveRoomWhenRoomDoesntExits() {
        // Given
        Mockito.when(roomRepository.saveRoomToDatabase(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(true);

        // When
        boolean actual = underTest.createRoom(ROOMNAME_HOLLYWOOD,
                CHAIRSROWSNUMBER_HOLLYWOOD,
                CHAIRSCOLSNUMBER_HOLLYWOOD);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(roomRepository)
                .saveRoomToDatabase(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreateMovieShouldReturnFalseWhenMovieAlreadyExists() {
        // Given
        Mockito.when(roomRepository.saveRoomToDatabase(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(false);

        // When
        boolean actual = underTest.createRoom(ROOMNAME_HOLLYWOOD,
                CHAIRSROWSNUMBER_HOLLYWOOD,
                CHAIRSCOLSNUMBER_HOLLYWOOD);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(roomRepository)
                .saveRoomToDatabase(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testListRoomsShouldReturnRoomsList() {
        // Given
        List<RoomEntity> rooms = new ArrayList<>();
        rooms.add(ROOM_ENTITY_HOLLYWOOD);
        rooms.add(ROOM_ENTITY_LONGISLAND);

        List<Room> expected = new ArrayList<>();
        expected.add(ROOM_HOLLYWOOD);
        expected.add(ROOM_LONGISLAND);

        Mockito.when(roomRepository.findAllRooms())
                .thenReturn(rooms);

        Mockito.when(roomEntityMapper.mapRoomEntity(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(ROOM_HOLLYWOOD);

        Mockito.when(roomEntityMapper.mapRoomEntity(ROOM_ENTITY_LONGISLAND))
                .thenReturn(ROOM_LONGISLAND);

        // When
        List<Room> actual = underTest.listRooms();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDeleteRoomShouldReturnTrueWhenDeletionComplete() {
        // Given
        Mockito.when(roomRepository.deleteRoomFromDatabase(ROOMNAME_HOLLYWOOD))
                .thenReturn(true);

        // When
        boolean actual = underTest.deleteRoom(ROOMNAME_HOLLYWOOD);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(roomRepository)
                .deleteRoomFromDatabase(ROOMNAME_HOLLYWOOD);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testDeleteRoomShouldReturnFalseWhenDeletionFailedBecauseRoomAlreadyExists() {
        // Given
        Mockito.when(roomRepository.deleteRoomFromDatabase(ROOMNAME_HOLLYWOOD))
                .thenReturn(false);

        // When
        boolean actual = underTest.deleteRoom(ROOMNAME_HOLLYWOOD);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(roomRepository)
                .deleteRoomFromDatabase(ROOMNAME_HOLLYWOOD);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdateRoomShouldReturnTrueWhenRoomUpdateIsSuccessful() {
        // Given
        Mockito.when(roomRepository.updateRoomFromDatabase(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(true);

        // When
        boolean actual = underTest.updateRoom(ROOMNAME_HOLLYWOOD,
                CHAIRSROWSNUMBER_HOLLYWOOD,
                CHAIRSCOLSNUMBER_HOLLYWOOD);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(roomRepository)
                .updateRoomFromDatabase(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdateMovieShouldReturnFalseWhenMovieUpdateIsFailedBecauseMovieDoesntExists() {
        // Given
        Mockito.when(roomRepository.updateRoomFromDatabase(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(false);

        // When
        boolean actual = underTest.updateRoom(ROOMNAME_HOLLYWOOD,
                CHAIRSROWSNUMBER_HOLLYWOOD,
                CHAIRSCOLSNUMBER_HOLLYWOOD);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(roomRepository)
                .updateRoomFromDatabase(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

}
