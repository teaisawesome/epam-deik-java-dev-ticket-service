package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entities.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.mappers.ScreeningEntityMapper;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.impl.exceptions.ScreeningOverlapException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningEntityMapper screeningEntityMapper;

    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository,
                                MovieRepository movieRepository,
                                RoomRepository roomRepository,
                                ScreeningEntityMapper screeningEntityMapper) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.screeningEntityMapper = screeningEntityMapper;
    }

    @Override
    public void createScreening(String movieTitle,
                                String roomName,
                                Date startScreening)
            throws MovieNotFoundException, RoomNotFoundException, ScreeningOverlapException {

        MovieEntity movieEntity = movieRepository.getMovieEntityByTitle(movieTitle);

        RoomEntity roomEntity = roomRepository.getRoomEntityByName(roomName);

        if (screeningRepository.isScreeningExistsByRoom(roomEntity)) {
            List<ScreeningEntity> screenings = screeningRepository
                    .findAllScreeningByRoom(roomEntity);

            Date plannedScreeningEndTime = DateUtils.addMinutes(startScreening, movieEntity.getLength());

            for (ScreeningEntity screeningEntity:
                    screenings) {
                Date currentStartScreening = screeningEntity.getStartTime();
                Date currentScreeningEndTime = DateUtils.addMinutes(
                        currentStartScreening,
                        screeningEntity.getMovie().getLength());

                Date currentScreeningEndTimeWithBreak = DateUtils.addMinutes(currentScreeningEndTime, 10);

                if (isScreeningOverlapping(currentStartScreening,
                        currentScreeningEndTime,
                        startScreening,
                        plannedScreeningEndTime)) {
                    throw new ScreeningOverlapException("There is an overlapping screening");
                }

                if (isScreeningOverlapping(currentStartScreening, currentScreeningEndTimeWithBreak,
                        startScreening, plannedScreeningEndTime)) {
                    throw new ScreeningOverlapException(
                            "This would start in the break period after another screening in this room"
                    );
                }
            }

            ScreeningEntity screeningEntity = new ScreeningEntity(movieEntity, roomEntity, startScreening);

            screeningRepository.createScreening(screeningEntity);
        } else {
            ScreeningEntity screeningEntity = new ScreeningEntity(movieEntity, roomEntity, startScreening);

            screeningRepository.createScreening(screeningEntity);
        }
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAllScreening()
                .stream()
                .map(screeningEntity -> screeningEntityMapper.mapScreeningEntity(screeningEntity))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteScreening(String movieTitle, String roomName, Date startScreening)
            throws RoomNotFoundException, MovieNotFoundException {
        MovieEntity movieEntity = movieRepository.getMovieEntityByTitle(movieTitle);

        RoomEntity roomEntity = roomRepository.getRoomEntityByName(roomName);

        return screeningRepository.deleteScreening(movieEntity, roomEntity, startScreening);
    }

    private boolean isScreeningOverlapping(Date startScreening, Date screeningEndTime,
                                   Date plannedStartTime, Date plannedScreeningEndTime) {
        return startScreening.before(plannedScreeningEndTime)
                && screeningEndTime.after(plannedStartTime);
    }


}
