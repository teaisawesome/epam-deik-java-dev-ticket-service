package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.domain.user.Screening;
import com.epam.training.ticketservice.presentation.cli.helpers.ListToStringConverter;
import com.epam.training.ticketservice.presentation.cli.helpers.ScreeningStartDateConverter;
import com.epam.training.ticketservice.repository.impl.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.impl.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.service.LoginService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.impl.exceptions.ScreeningOverlapException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ShellComponent
public class ScreeningCommandHandler extends AbstractAuthenticatedCommand {
    private ScreeningService screeningService;
    private ScreeningStartDateConverter screeningStartDateConverter;
    private ListToStringConverter listToStringConverter;

    public ScreeningCommandHandler(LoginService loginService,
                                   ScreeningService screeningService,
                                   ScreeningStartDateConverter screeningStartDateConverter,
                                   ListToStringConverter listToStringConverter) {
        super(loginService);
        this.screeningService = screeningService;
        this.screeningStartDateConverter = screeningStartDateConverter;
        this.listToStringConverter = listToStringConverter;
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Create new screening", key = "create screening")
    public String createScreening(String movieTitle, String roomName, String screeningStartTime) {
        try {
            Date screeningStartTimeInDate = screeningStartDateConverter.convertStringDateToDate(screeningStartTime);
            screeningService.createScreening(movieTitle, roomName, screeningStartTimeInDate);
        } catch (ParseException e) {
            return e.getMessage();
        } catch (RoomNotFoundException e) {
            return e.getMessage();
        } catch (MovieNotFoundException e) {
            return e.getMessage();
        } catch (ScreeningOverlapException e) {
            return e.getMessage();
        }

        return "Screening Creation Successful!";
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public String listAllRooms() {
        List<Screening> screenings = screeningService.listScreenings();

        if (screenings.isEmpty()) {
            return "There are no screenings";
        }

        return listToStringConverter.convertScreenings(screenings);
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Delete screening from screenings", key = "delete screening")
    public String deleteScreening(String movieTitle, String roomName, String screeningStartTime) {
        boolean isDeletionSucceeded = false;

        try {
            Date screeningStartTimeInDate = screeningStartDateConverter.convertStringDateToDate(screeningStartTime);
            isDeletionSucceeded =  screeningService.deleteScreening(movieTitle, roomName, screeningStartTimeInDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isDeletionSucceeded ? "Screening Deleted!"
                    : "Screening Deletion Failed! Screening not found!";
    }
}
