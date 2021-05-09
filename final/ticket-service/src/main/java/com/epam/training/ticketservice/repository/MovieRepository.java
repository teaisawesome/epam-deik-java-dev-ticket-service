package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;

import java.util.List;

public interface MovieRepository {
    boolean saveMovieToDatabase(MovieEntity movieEntity);

    boolean deleteMovieFromDatabase(String title);

    List<MovieEntity> findAllMovies();

    boolean updateMovieFromDatabase(MovieEntity movieEntity);
}
