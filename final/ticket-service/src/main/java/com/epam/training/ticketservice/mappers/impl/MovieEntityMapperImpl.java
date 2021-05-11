package com.epam.training.ticketservice.mappers.impl;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.mappers.MovieEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieEntityMapperImpl implements MovieEntityMapper {
    @Override
    public Movie mapMovieEntity(MovieEntity movieEntity) {
        return new Movie(movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getLength());
    }
}
