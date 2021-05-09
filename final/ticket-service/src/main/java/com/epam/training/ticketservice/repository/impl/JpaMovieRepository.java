package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private MovieDao movieDao;

    @Autowired
    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
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
}
