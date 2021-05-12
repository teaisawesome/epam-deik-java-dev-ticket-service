package com.epam.training.ticketservice.helpers;

import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.presentation.cli.helpers.impl.ListToStringConverterImpl;
import com.epam.training.ticketservice.presentation.cli.helpers.impl.ScreeningStartDateConverterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListToStringConverterImplTest {
    private ScreeningStartDateConverterImpl screeningStartDateConverter;
    private ListToStringConverterImpl underTest;


    @BeforeEach
    public void init() {
        screeningStartDateConverter = Mockito.mock(ScreeningStartDateConverterImpl.class);
        underTest = new ListToStringConverterImpl(screeningStartDateConverter);
    }

    @Test
    public void testConvertToMoviesShouldReturnTheCorrectMoviesListInString() {
        // Given
        String expected = "Alien (horror, 110 minutes)";
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Alien", "horror", 110));

        // When
        String actual = underTest.convertMovies(movies);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertRoomsShouldReturnTheCorrectRoomsListInString() {
        // Given
        String expected = "Room Hollywood with 100 seats, 10 rows and 10 columns";
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Hollywood", 10, 10));

        // When
        String actual = underTest.convertRooms(rooms);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertScreenings() throws ParseException {
        // Given
        String expected = "Alien (horror, 110 minutes), screened in room Hollywood, at 2021-03-15 11:00";
        List<Screening> screenings = new ArrayList<>();
        Movie movie = new Movie("Alien", "horror", 110);
        Room room = new Room("Hollywood", 10, 10);
        String dateInString = "2021-03-15 11:00";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateInString);

        Mockito.when(screeningStartDateConverter.convertDateToStringDate(date))
                .thenReturn("2021-03-15 11:00");
        screenings.add(new Screening(movie, room, date));

        // When
        String actual = underTest.convertScreenings(screenings);

        // Then
        Assertions.assertEquals(expected, actual);
    }
}
