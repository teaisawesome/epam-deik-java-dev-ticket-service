package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.mappers.MovieEntityMapper;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private MovieDao movieDao;
    private final MovieEntityMapper movieEntityMapper;

    @Autowired
    public JpaMovieRepository(MovieDao movieDao, MovieEntityMapper movieEntityMapper) {
        this.movieDao = movieDao;
        this.movieEntityMapper = movieEntityMapper;
    }

    @Override
    public boolean saveMovieToDatabase(MovieEntity movieEntity) {
        if (movieDao.existsByTitle(movieEntity.getTitle())) {
            return false;
        }

        movieDao.save(movieEntity);

        return true;
    }

    @Override
    public boolean deleteMovieFromDatabase(String title) {
        if (!movieDao.existsByTitle(title)) {
            return false;
        }

        movieDao.deleteByTitle(title);

        return true;
    }

    @Override
    public List<MovieEntity> findAllMovies() {
        return movieDao.findAll();
    }

    @Override
    public boolean updateMovieFromDatabase(MovieEntity movieEntity) {
        if (!movieDao.existsByTitle(movieEntity.getTitle())) {
            return false;
        }

        MovieEntity updatedMovieEntity = movieDao.getMovieEntityByTitle(movieEntity.getTitle());
        updatedMovieEntity.setGenre(movieEntity.getGenre());
        updatedMovieEntity.setLength(movieEntity.getLength());

        movieDao.save(updatedMovieEntity);

        return true;
    }

    @Override
    public MovieEntity getMovieEntityByTitle(String title) throws MovieNotFoundException {
        if (!movieDao.existsByTitle(title)) {
            throw new MovieNotFoundException("Movie Not Found!");
        }

        return movieDao.getMovieEntityByTitle(title);
    }
}
