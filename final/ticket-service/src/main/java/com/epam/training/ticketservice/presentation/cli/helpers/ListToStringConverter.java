package com.epam.training.ticketservice.presentation.cli.helpers;

import com.epam.training.ticketservice.domain.user.Movie;

import java.util.List;

public interface ListToStringConverter {
    String convertMovies(List<Movie> movies);
}
