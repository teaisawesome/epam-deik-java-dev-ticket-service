package com.epam.training.ticketservice.presentation.cli.helpers;

import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.domain.user.Screening;

import java.util.List;

public interface ListToStringConverter {
    String convertMovies(List<Movie> movies);

    String convertRooms(List<Room> rooms);

    String convertScreenings(List<Screening> screenings);
}
