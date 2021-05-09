package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.service.LoginService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class MovieCommandHandler extends AbstractAuthenticatedCommand{

    public MovieCommandHandler(LoginService loginService) {
        super(loginService);
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Create new movie to movies", key = "create movie")
    public String createMovie(String title, String genre, int length) {
        return "Movie Created" + title;
    }
}
