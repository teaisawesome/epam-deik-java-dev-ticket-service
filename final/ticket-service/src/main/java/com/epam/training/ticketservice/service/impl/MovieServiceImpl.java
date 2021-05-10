package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public boolean createMovie(String title, String genre, int length) {
        MovieEntity movieEntity = new MovieEntity(title, genre, length);

        return movieRepository.saveMovieToDatabase(movieEntity);
    }

    @Override
    public boolean deleteMovie(String title) {
        return movieRepository.deleteMovieFromDatabase(title);
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAllMovies()
                .stream()
                .map(movieEntity -> mapMovieEntity(movieEntity)).collect(Collectors.toList());
    }

    @Override
    public boolean updateMovie(String title, String genre, int length) {
        MovieEntity movieEntity = new MovieEntity(title, genre, length);
        return movieRepository.updateMovieFromDatabase(movieEntity);
    }

    private Movie mapMovieEntity(MovieEntity movieEntity) {
        return new Movie(movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getLength());
    }
}
