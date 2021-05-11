package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.domain.user.Room;
import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.impl.exceptions.ScreeningOverlapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void createScreening(String movieTitle,
                                String roomName,
                                Date startScreening)
            throws MovieNotFoundException, RoomNotFoundException, ScreeningOverlapException {

        Movie screenedMovie = movieRepository.getMovieByTitle(movieTitle);

        Room screeningRoom = roomRepository.getRoomByName(roomName);

        MovieEntity movieEntity = movieRepository.getMovieEntityByTitle(movieTitle);

        RoomEntity roomEntity = roomRepository.getRoomEntityByName(roomName);

        if (screeningRepository.isScreeningExistsByRoom(roomRepository.getRoomEntityByName(roomName))) {
            List<ScreeningEntity> screenings = screeningRepository
                    .findAllScreeningByRoom(roomRepository.getRoomEntityByName(roomName));

            Date plannedScreeningEndTime = DateUtils.addMinutes(startScreening, screenedMovie.getLength());

            for (ScreeningEntity screeningEntity:
                    screenings) {
                Date currentStartScreening = screeningEntity.getStartTime();
                Date currentScreeningEndTime = DateUtils.addMinutes(
                        currentStartScreening,
                        screeningEntity.getMovie().getLength());

                Date currentScreeningEndTimeWithBreak = DateUtils.addMinutes(currentScreeningEndTime, 10);

                if (isScreeningOverlapping(currentStartScreening, currentScreeningEndTime, startScreening, plannedScreeningEndTime)) {
                    throw new ScreeningOverlapException("There is an overlapping screening");
                }

                if (isScreeningOverlapping(currentStartScreening, currentScreeningEndTimeWithBreak,
                        startScreening, plannedScreeningEndTime)) {
                    throw new ScreeningOverlapException("This would start in the break period after another screening in this room");
                }
            }

            ScreeningEntity screeningEntity = new ScreeningEntity(movieEntity, roomEntity, startScreening);

            screeningRepository.createScreening(screeningEntity);
        }
        else {
            ScreeningEntity screeningEntity = new ScreeningEntity(movieEntity, roomEntity, startScreening);

            screeningRepository.createScreening(screeningEntity);
        }
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAllScreening()
                .stream()
                .map(screeningEntity -> mapScreeningEntity(screeningEntity))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteScreening(String movieTitle, String roomName, Date startScreening) {
        MovieEntity movieEntity = movieRepository.getMovieEntityByTitle(movieTitle);

        RoomEntity roomEntity = roomRepository.getRoomEntityByName(roomName);

        return screeningRepository.deleteScreening(movieEntity, roomEntity, startScreening);
    }

    private boolean isScreeningOverlapping(Date startScreening, Date screeningEndTime,
                                   Date plannedStartTime, Date plannedScreeningEndTime) {
        return startScreening.before(plannedScreeningEndTime)
                && screeningEndTime.after(plannedStartTime);
    }

    private Screening mapScreeningEntity(ScreeningEntity screeningEntity) {
        Movie movie = mapMovieEntity(screeningEntity.getMovie());
        Room room = mapRoomEntity(screeningEntity.getRoom());

        return new Screening(movie, room, screeningEntity.getStartTime());
    }

    private Movie mapMovieEntity(MovieEntity movieEntity) {
        return new Movie(movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getLength());
    }

    private Room mapRoomEntity(RoomEntity roomEntity) {
        return new Room(roomEntity.getName(), roomEntity.getChairsRowsNumber(), roomEntity.getChairsColsNumber());
    }
}
