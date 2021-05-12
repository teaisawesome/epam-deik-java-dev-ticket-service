package com.epam.training.ticketservice.mappers.impl;

import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.mappers.MovieEntityMapper;
import com.epam.training.ticketservice.mappers.RoomEntityMapper;
import com.epam.training.ticketservice.mappers.ScreeningEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ScreeningEntityMapperImpl implements ScreeningEntityMapper {

    private MovieEntityMapper movieEntityMapper;

    private RoomEntityMapper roomEntityMapper;

    public ScreeningEntityMapperImpl(MovieEntityMapper movieEntityMapper, RoomEntityMapper roomEntityMapper) {
        this.movieEntityMapper = movieEntityMapper;
        this.roomEntityMapper = roomEntityMapper;
    }

    @Override
    public Screening mapScreeningEntity(ScreeningEntity screeningEntity) {
        Movie movie = movieEntityMapper.mapMovieEntity(screeningEntity.getMovie());
        Room room = roomEntityMapper.mapRoomEntity(screeningEntity.getRoom());

        return new Screening(movie, room, screeningEntity.getStartTime());
    }
}
