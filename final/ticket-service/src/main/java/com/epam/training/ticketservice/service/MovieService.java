package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.user.Movie;

import java.util.List;

public interface MovieService {
    boolean createMovie(String title, String genre, int length);

    boolean deleteMovie(String title);

    List<Movie> listMovies();

    boolean updateMovie(String title, String genre, int length);
}
