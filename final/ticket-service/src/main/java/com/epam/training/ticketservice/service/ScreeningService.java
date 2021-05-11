package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.service.impl.exceptions.ScreeningOverlapException;

import java.util.Date;
import java.util.List;

public interface ScreeningService {
    void createScreening(String movieTitle,
                         String roomName,
                         Date startScreening)
            throws MovieNotFoundException, RoomNotFoundException, ScreeningOverlapException;

    List<Screening> listScreenings();

    boolean deleteScreening(String movieTitle,
                            String roomName,
                            Date startScreening);
}
