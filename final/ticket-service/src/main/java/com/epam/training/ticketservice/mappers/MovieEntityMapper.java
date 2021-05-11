package com.epam.training.ticketservice.mappers;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;

public interface MovieEntityMapper {
    Movie mapMovieEntity(MovieEntity movieEntity);
}
