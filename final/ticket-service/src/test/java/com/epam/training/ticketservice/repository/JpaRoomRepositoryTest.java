package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.mappers.RoomEntityMapper;
import com.epam.training.ticketservice.repository.impl.JpaRoomRepository;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class JpaRoomRepositoryTest {

    private RoomDao roomDao;
    private RoomEntityMapper roomEntityMapper;
    private JpaRoomRepository underTest;

    private final String ROOMNAME_LONGISLAND = "LongIsland";
    private final int CHAIRSROWSNUMBER_LONGISLAND = 10;
    private final int CHAIRSCOLSNUMBER_LONGISLAND = 10;
    private final RoomEntity ROOM_ENTITY_LONGISLAND = new RoomEntity(ROOMNAME_LONGISLAND,
            CHAIRSROWSNUMBER_LONGISLAND,
            CHAIRSCOLSNUMBER_LONGISLAND);
    private final Room ROOM_LONGISLAND = new Room(ROOMNAME_LONGISLAND,
            CHAIRSROWSNUMBER_LONGISLAND,
            CHAIRSCOLSNUMBER_LONGISLAND);

    private final String ROOMNAME_HOLLYWOOD = "LongIsland";
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
        roomDao = Mockito.mock(RoomDao.class);
        roomEntityMapper = Mockito.mock(RoomEntityMapper.class);
        underTest = new JpaRoomRepository(roomDao, roomEntityMapper);
    }

    @Test
    public void testSaveRoomToDatabaseShouldReturnTrueWhenGivenRoomDoesntExists() {
        // Given
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(false);
        Mockito.when(roomDao.save(ROOM_ENTITY_LONGISLAND))
                .thenReturn(ROOM_ENTITY_LONGISLAND);

        // When
        boolean actual = underTest.saveRoomToDatabase(ROOM_ENTITY_LONGISLAND);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verify(roomDao).save(ROOM_ENTITY_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testSaveRoomToDatabaseShouldReturnFalseWhenGivenRoomAlreadyExists() {
        // Given
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(true);

        // When
        boolean actual = underTest.saveRoomToDatabase(ROOM_ENTITY_LONGISLAND);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testDeleteRoomFromDatabaseShouldReturnTrueAndDeleteRoomWhenRoomAlreadyExists() {
        // Given
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(true);

        // When
        boolean actual = underTest.deleteRoomFromDatabase(ROOMNAME_LONGISLAND);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verify(roomDao).deleteByName(ROOMNAME_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testDeleteRoomFromDatabaseShouldReturnFalseAndDeletionFailedWhenRoomDoesntExists() {
        // Given
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(false);

        // When
        boolean actual = underTest.deleteRoomFromDatabase(ROOMNAME_LONGISLAND);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testFindAllRoomsShouldReturnRoomEntityList() {
        // Given
        List<RoomEntity> expected = new ArrayList<>();
        expected.add(ROOM_ENTITY_LONGISLAND);
        expected.add(ROOM_ENTITY_HOLLYWOOD);
        Mockito.when(roomDao.findAll())
                .thenReturn(expected);

        // When
        List<RoomEntity> actual = underTest.findAllRooms();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(roomDao).findAll();
        Mockito.verifyNoMoreInteractions(roomDao);
    }


    @Test
    public void testUpdateRoomFromDatabaseShouldReturnTrueWhenRoomUpdateIsComplete() {
        // Given
        RoomEntity roomEntity = new RoomEntity(ROOMNAME_LONGISLAND,
                CHAIRSROWSNUMBER_LONGISLAND,
                CHAIRSCOLSNUMBER_LONGISLAND);

        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(true);
        Mockito.when(roomDao.getRoomEntityByName(ROOMNAME_LONGISLAND))
                .thenReturn(ROOM_ENTITY_LONGISLAND);
        Mockito.when(roomDao.save(roomEntity))
                .thenReturn(roomEntity);

        // When
        boolean actual = underTest.updateRoomFromDatabase(roomEntity);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verify(roomDao).getRoomEntityByName(ROOMNAME_LONGISLAND);
        Mockito.verify(roomDao).save(roomEntity);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testUpdateRoomFromDatabaseShouldReturnFalseWhenRoomUpdateIsFailedBecauseRoomDoesntExists() {
        // Given
        RoomEntity roomEntity = new RoomEntity(ROOMNAME_LONGISLAND,
                CHAIRSROWSNUMBER_LONGISLAND,
                CHAIRSCOLSNUMBER_LONGISLAND);
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(false);

        // When
        boolean actual = underTest.updateRoomFromDatabase(roomEntity);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testGetRoomEntityByNameShouldThrowRoomNotFoundExceptionWhenRoomDoesntExists() {
        // Given
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(false);

        // When
        Assertions.assertThrows(RoomNotFoundException.class, () -> underTest.getRoomEntityByName(ROOMNAME_LONGISLAND));

        // Then
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }

    @Test
    public void testGetRoomEntityByNameShouldReturnWithRoomEntityByGivenRoomName() {
        // Given
        Mockito.when(roomDao.existsByName(ROOMNAME_LONGISLAND))
                .thenReturn(true);
        Mockito.when(roomDao.getRoomEntityByName(ROOMNAME_LONGISLAND))
                .thenReturn(ROOM_ENTITY_LONGISLAND);
        RoomEntity expected = ROOM_ENTITY_LONGISLAND;


        RoomEntity actual = null;
        // When
        try {
            actual = underTest.getRoomEntityByName(ROOMNAME_LONGISLAND);
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(roomDao).existsByName(ROOMNAME_LONGISLAND);
        Mockito.verify(roomDao).getRoomEntityByName(ROOMNAME_LONGISLAND);
        Mockito.verifyNoMoreInteractions(roomDao);
    }
}
