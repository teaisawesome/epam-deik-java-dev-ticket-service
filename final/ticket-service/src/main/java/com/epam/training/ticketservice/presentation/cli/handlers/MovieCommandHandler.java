package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.presentation.cli.helpers.ListToStringConverter;
import com.epam.training.ticketservice.service.LoginService;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class MovieCommandHandler extends AbstractAuthenticatedCommand {

    private MovieService movieService;

    private ListToStringConverter listToStringConverter;

    public MovieCommandHandler(LoginService loginService,
                               MovieService movieService,
                               ListToStringConverter listToStringConverter) {
        super(loginService);
        this.movieService = movieService;
        this.listToStringConverter = listToStringConverter;
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Create new movie to movies", key = "create movie")
    public String createMovie(String title, String genre, int length) {
        return movieService.createMovie(title, genre, length)
                ? "Movie Created!"
                : "Movie Creation Failed! Movie with given title already exists!";
    }

    @ShellMethodAvailability(value = "loggedIn")
    @ShellMethod(value = "List movies", key = "list movies")
    public String listAllMovies() {
        List<Movie> movies = movieService.listMovies();

        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }

        return listToStringConverter.convertMovies(movies);
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Delete movie from movies", key = "update movie")
    public String updateMovie(String title, String genre, int length) {
        return movieService.updateMovie(title, genre, length)
                ? "Movie Updated!"
                : "Movie Update Failed! Movie with given title not exists!";
    }

    @ShellMethodAvailability(value = "admin")
    @ShellMethod(value = "Delete movie from movies", key = "delete movie")
    public String deleteMovie(String title) {
        return movieService.deleteMovie(title)
                ? "Movie Deleted!"
                : "Movie Deletion Failed! Movie with given title not exists!";
    }
}
