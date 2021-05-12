package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.mappers.ScreeningEntityMapper;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.service.impl.ScreeningServiceImpl;
import com.epam.training.ticketservice.service.impl.exceptions.ScreeningOverlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScreeningServiceImplTest {
    private ScreeningServiceImpl underTest;
    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;
    private ScreeningEntityMapper screeningEntityMapper;

    private final String TITLE_ALIEN = "Alien";
    private final String GENRE_ALIEN = "horror";
    private final int LENGTH_ALIEN = 60;
    private final MovieEntity MOVIE_ENTITY_ALIEN = new MovieEntity(TITLE_ALIEN, GENRE_ALIEN, LENGTH_ALIEN) ;
    private final Movie MOVIE_ALIEN = new Movie(TITLE_ALIEN, GENRE_ALIEN, LENGTH_ALIEN);


    private final String ROOMNAME_HOLLYWOOD = "Hollywood";
    private final int CHAIRSROWSNUMBER_HOLLYWOOD = 8;
    private final int CHAIRSCOLSNUMBER_HOLLYWOOD = 5;
    private final RoomEntity ROOM_ENTITY_HOLLYWOOD = new RoomEntity(ROOMNAME_HOLLYWOOD,
            CHAIRSROWSNUMBER_HOLLYWOOD,
            CHAIRSCOLSNUMBER_HOLLYWOOD);
    private final Room ROOM_HOLLYWOOD = new Room(ROOMNAME_HOLLYWOOD,
            CHAIRSROWSNUMBER_HOLLYWOOD,
            CHAIRSCOLSNUMBER_HOLLYWOOD);

    private final Date startScreeningDateInDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-11-20 16:00");
    private final Date overlappingStartScreeningDateInDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-11-20 16:30");
    private final Date overlappingBreakStartScreeningDateInDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-11-20 17:09");
    private final Date noOverlappingStartScreeningDateInDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-11-20 17:10");

    private final ScreeningEntity screeningEntity = new ScreeningEntity(MOVIE_ENTITY_ALIEN,
            ROOM_ENTITY_HOLLYWOOD,
            startScreeningDateInDateFormat);

    private final ScreeningEntity noOverlappingPlannedScreeningEntity = new ScreeningEntity(MOVIE_ENTITY_ALIEN,
            ROOM_ENTITY_HOLLYWOOD,
            noOverlappingStartScreeningDateInDateFormat);


    Screening screening = new Screening(MOVIE_ALIEN,
            ROOM_HOLLYWOOD,
            startScreeningDateInDateFormat);


    public ScreeningServiceImplTest() throws ParseException {
    }

    @BeforeEach
    public void init() {
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        screeningEntityMapper = Mockito.mock(ScreeningEntityMapper.class);
        underTest = new ScreeningServiceImpl(screeningRepository,
                movieRepository,
                roomRepository,
                screeningEntityMapper);
    }

    @Test
    public void testListScreeningsShouldReturnWithListOfScreenings() {

        // Given
        List<Screening> expected = new ArrayList<>();
        expected.add(screening);
        List<ScreeningEntity> screeningEntities = new ArrayList<>();
        screeningEntities.add(screeningEntity);

        Mockito.when(screeningRepository.findAllScreening())
                .thenReturn(screeningEntities);

        Mockito.when(screeningEntityMapper.mapScreeningEntity(screeningEntity))
                .thenReturn(screening);

        // When
        List<Screening> actual = underTest.listScreenings();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(screeningRepository).findAllScreening();
        Mockito.verify(screeningEntityMapper).mapScreeningEntity(screeningEntity);
        Mockito.verifyNoMoreInteractions(screeningRepository);
        Mockito.verifyNoMoreInteractions(screeningEntityMapper);
    }

    @Test
    public void testCreateScreeningShouldSaveScreeningIfScreeningDoesntExistsInDatabase() throws MovieNotFoundException, RoomNotFoundException, ScreeningOverlapException {
        // Given
        Mockito.when(movieRepository.getMovieEntityByTitle(TITLE_ALIEN))
                .thenReturn(MOVIE_ENTITY_ALIEN);
        Mockito.when(roomRepository.getRoomEntityByName(ROOMNAME_HOLLYWOOD))
                .thenReturn(ROOM_ENTITY_HOLLYWOOD);
        Mockito.when(screeningRepository.isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(false);

        // When
        underTest.createScreening(TITLE_ALIEN, ROOMNAME_HOLLYWOOD, startScreeningDateInDateFormat);

        // Then
        Mockito.verify(movieRepository).getMovieEntityByTitle(TITLE_ALIEN);
        Mockito.verify(roomRepository).getRoomEntityByName(ROOMNAME_HOLLYWOOD);
        Mockito.verify(screeningRepository).isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verify(screeningRepository).createScreening(screeningEntity);
    }

    @Test
    public void testCreateScreeningShouldThrowScreeningOverlapExceptionWhenPlannedScreeningOverlapWithOtherScreeningsOnTheScreeningRoom () throws MovieNotFoundException, RoomNotFoundException {
        // Given
        List<ScreeningEntity> screeningEntities = new ArrayList<>();
        screeningEntities.add(screeningEntity);

        Mockito.when(movieRepository.getMovieEntityByTitle(TITLE_ALIEN))
                .thenReturn(MOVIE_ENTITY_ALIEN);
        Mockito.when(roomRepository.getRoomEntityByName(ROOMNAME_HOLLYWOOD))
                .thenReturn(ROOM_ENTITY_HOLLYWOOD);
        Mockito.when(screeningRepository.isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(true);
        Mockito.when(screeningRepository.findAllScreeningByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(screeningEntities);

        // When
        Assertions.assertThrows(ScreeningOverlapException.class, () -> underTest.createScreening(TITLE_ALIEN, ROOMNAME_HOLLYWOOD, overlappingStartScreeningDateInDateFormat));

        // Then
        Mockito.verify(movieRepository).getMovieEntityByTitle(TITLE_ALIEN);
        Mockito.verify(roomRepository).getRoomEntityByName(ROOMNAME_HOLLYWOOD);
        Mockito.verify(screeningRepository).isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verify(screeningRepository).findAllScreeningByRoom(ROOM_ENTITY_HOLLYWOOD);
    }

    @Test
    public void testCreateScreeningShouldThrowScreeningOverlapExceptionWhenPlannedScreeningOverlapWithOtherScreeningsBreakOnTheScreeningRoom() throws MovieNotFoundException, RoomNotFoundException {
        // Given
        List<ScreeningEntity> screeningEntities = new ArrayList<>();
        screeningEntities.add(screeningEntity);

        Mockito.when(movieRepository.getMovieEntityByTitle(TITLE_ALIEN))
                .thenReturn(MOVIE_ENTITY_ALIEN);
        Mockito.when(roomRepository.getRoomEntityByName(ROOMNAME_HOLLYWOOD))
                .thenReturn(ROOM_ENTITY_HOLLYWOOD);
        Mockito.when(screeningRepository.isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(true);
        Mockito.when(screeningRepository.findAllScreeningByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(screeningEntities);

        // When
        Assertions.assertThrows(ScreeningOverlapException.class, () -> underTest.createScreening(TITLE_ALIEN, ROOMNAME_HOLLYWOOD, overlappingBreakStartScreeningDateInDateFormat));

        // Then
        Mockito.verify(movieRepository).getMovieEntityByTitle(TITLE_ALIEN);
        Mockito.verify(roomRepository).getRoomEntityByName(ROOMNAME_HOLLYWOOD);
        Mockito.verify(screeningRepository).isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verify(screeningRepository).findAllScreeningByRoom(ROOM_ENTITY_HOLLYWOOD);
    }

    @Test
    public void testCreateScreeningShouldSaveThePlannedScreeningWithNoOverlapping() throws MovieNotFoundException, RoomNotFoundException, ScreeningOverlapException {

        // Given
        List<ScreeningEntity> screeningEntities = new ArrayList<>();
        screeningEntities.add(screeningEntity);

        Mockito.when(movieRepository.getMovieEntityByTitle(TITLE_ALIEN))
                .thenReturn(MOVIE_ENTITY_ALIEN);
        Mockito.when(roomRepository.getRoomEntityByName(ROOMNAME_HOLLYWOOD))
                .thenReturn(ROOM_ENTITY_HOLLYWOOD);
        Mockito.when(screeningRepository.isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(true);
        Mockito.when(screeningRepository.findAllScreeningByRoom(ROOM_ENTITY_HOLLYWOOD))
                .thenReturn(screeningEntities);

        // When
        underTest.createScreening(TITLE_ALIEN, ROOMNAME_HOLLYWOOD, noOverlappingStartScreeningDateInDateFormat);

        // Then
        Mockito.verify(movieRepository).getMovieEntityByTitle(TITLE_ALIEN);
        Mockito.verify(roomRepository).getRoomEntityByName(ROOMNAME_HOLLYWOOD);
        Mockito.verify(screeningRepository).isScreeningExistsByRoom(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verify(screeningRepository).findAllScreeningByRoom(ROOM_ENTITY_HOLLYWOOD);
        Mockito.verify(screeningRepository).createScreening(noOverlappingPlannedScreeningEntity);
    }

    @Test
    public void testDeleteScreening() throws RoomNotFoundException, MovieNotFoundException {
        // Given
        Mockito.when(roomRepository.getRoomEntityByName(ROOMNAME_HOLLYWOOD))
                .thenReturn(ROOM_ENTITY_HOLLYWOOD);

        Mockito.when(movieRepository.getMovieEntityByTitle(TITLE_ALIEN))
                .thenReturn(MOVIE_ENTITY_ALIEN);

        Mockito.when(screeningRepository.deleteScreening(MOVIE_ENTITY_ALIEN, ROOM_ENTITY_HOLLYWOOD, startScreeningDateInDateFormat))
                .thenReturn(true);

        // When
        boolean actual = underTest.deleteScreening(TITLE_ALIEN, ROOMNAME_HOLLYWOOD, startScreeningDateInDateFormat);

        // Then
        Assertions.assertTrue(actual);
    }
}
