package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieRepository {
    boolean saveMovieToDatabase(MovieEntity movieEntity);

    boolean deleteMovieFromDatabase(String title);

    List<MovieEntity> findAllMovies();

    boolean updateMovieFromDatabase(MovieEntity movieEntity);

    MovieEntity getMovieEntityByTitle(String title) throws MovieNotFoundException;
}
