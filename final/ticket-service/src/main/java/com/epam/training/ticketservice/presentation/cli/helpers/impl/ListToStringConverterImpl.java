package com.epam.training.ticketservice.presentation.cli.helpers.impl;


import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.presentation.cli.helpers.ListToStringConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListToStringConverterImpl implements ListToStringConverter {

    private final ScreeningStartDateConverterImpl screeningStartDateConverter;

    public ListToStringConverterImpl(ScreeningStartDateConverterImpl screeningStartDateConverter) {
        this.screeningStartDateConverter = screeningStartDateConverter;
    }

    @Override
    public String convertMovies(List<Movie> movies) {
        return movies.stream()
                .map(movie -> movie.getTitle() + " " + "(" + movie.getGenre() + ", " + movie.getLength() + " minutes)")
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String convertRooms(List<Room> rooms) {
        return rooms.stream()
                .map(room -> "Room "
                        + room.getName()
                        + " with "
                        + (room.getChairsRowsNumber() * room.getChairsColsNumber())
                        + " seats, "
                        + room.getChairsRowsNumber()
                        + " rows and "
                        + room.getChairsColsNumber()
                        + " columns")
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String convertScreenings(List<Screening> screenings) {
        return screenings.stream()
                .map(screening -> screening.getMovieOnScreen().getTitle()
                        + " ("
                        + screening.getMovieOnScreen().getGenre()
                        + ", "
                        + screening.getMovieOnScreen().getLength()
                        + " minutes), screened in room "
                        + screening.getScreeningRoom().getName()
                        + ", at "
                        + screeningStartDateConverter.convertDateToStringDate(screening.getScreeningTime()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
