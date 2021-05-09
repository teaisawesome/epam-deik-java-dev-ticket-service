package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface MovieDao extends JpaRepository<MovieEntity, UUID> {
    boolean existsByTitle(String title);

    @Transactional
    void deleteByTitle(String title);

    MovieEntity getMovieEntityByTitle(String title);
}
