package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.mappers.MovieEntityMapper;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieEntityMapper movieEntityMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieEntityMapper movieEntityMapper) {
        this.movieRepository = movieRepository;
        this.movieEntityMapper = movieEntityMapper;
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
                .map(movieEntity -> movieEntityMapper.mapMovieEntity(movieEntity)).collect(Collectors.toList());
    }

    @Override
    public boolean updateMovie(String title, String genre, int length) {
        MovieEntity movieEntity = new MovieEntity(title, genre, length);
        return movieRepository.updateMovieFromDatabase(movieEntity);
    }
}
