package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MovieServiceImplTest {
    private MovieRepository movieRepository;

    private MovieServiceImpl underTest;

    private final String TITLE_ALIEN = "Alien";
    private final String GENRE_ALIEN = "horror";
    private final int LENGTH_ALIEN = 122;
    private final MovieEntity MOVIE_ENTITY_ALIEN = new MovieEntity(TITLE_ALIEN, GENRE_ALIEN, LENGTH_ALIEN) ;

    private final String TITLE_AVENGERS = "Avengers";
    private final String GENRE_AVENGERS = "action";
    private final int LENGTH_AVENGERS = 143;
    private final MovieEntity MOVIE_ENTITY_AVENGERS = new MovieEntity(TITLE_AVENGERS, GENRE_AVENGERS, LENGTH_AVENGERS) ;

    private final Movie MOVIE_ALIEN = new Movie(TITLE_ALIEN, GENRE_ALIEN, LENGTH_ALIEN);
    private final Movie MOVIE_AVENGERS = new Movie(TITLE_AVENGERS, GENRE_AVENGERS, LENGTH_AVENGERS);

    @BeforeEach
    public void init() {
        movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testCreateMovieShouldSaveMovieWhenMovieDoesntExists() {
        // Given
        Mockito.when(movieRepository.saveMovieToDatabase(MOVIE_ENTITY_ALIEN))
                .thenReturn(true);

        // When
        boolean actual = underTest.createMovie(TITLE_ALIEN, GENRE_ALIEN, LENGTH_ALIEN);

        // Then
        Assertions.assertEquals(true, actual);
        Mockito.verify(movieRepository)
                .saveMovieToDatabase(MOVIE_ENTITY_ALIEN);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreateMovieShouldReturnFalseWhenMovieAlreadyExists() {
        // Given
        Mockito.when(movieRepository.saveMovieToDatabase(MOVIE_ENTITY_ALIEN))
                .thenReturn(false);

        // When
        boolean actual = underTest.createMovie(TITLE_ALIEN, GENRE_ALIEN, LENGTH_ALIEN);

        // Then
        Assertions.assertEquals(false, actual);
        Mockito.verify(movieRepository)
                .saveMovieToDatabase(MOVIE_ENTITY_ALIEN);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testDeleteMovieShouldReturnTrueWhenDeletionComplete() {
        // Given
        Mockito.when(movieRepository.deleteMovieFromDatabase(TITLE_ALIEN))
                .thenReturn(true);

        // When
        boolean actual = underTest.deleteMovie(TITLE_ALIEN);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(movieRepository)
                .deleteMovieFromDatabase(TITLE_ALIEN);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testDeleteMovieShouldReturnFalseWhenDeletionFailedBecauseTitleAlreadyExists() {
        // Given
        Mockito.when(movieRepository.deleteMovieFromDatabase(TITLE_ALIEN))
                .thenReturn(false);

        // When
        boolean actual = underTest.deleteMovie(TITLE_ALIEN);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(movieRepository)
                .deleteMovieFromDatabase(TITLE_ALIEN);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testListMoviesShouldReturnMoviesList() {
        // Given
        List<MovieEntity> movies = new ArrayList<>();
        movies.add(MOVIE_ENTITY_ALIEN);
        movies.add(MOVIE_ENTITY_AVENGERS);

        List<Movie> expected = new ArrayList<>();
        expected.add(MOVIE_ALIEN);
        expected.add(MOVIE_AVENGERS);

        Mockito.when(movieRepository.findAllMovies())
                .thenReturn(movies);

        // When
        List<Movie> actual = underTest.listMovies();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateMovieShouldReturnTrueWhenMovieUpdateIsSuccessful() {
        // Given
        Mockito.when(movieRepository.updateMovieFromDatabase(MOVIE_ENTITY_AVENGERS))
                .thenReturn(true);

        // When
        boolean actual = underTest.updateMovie(TITLE_AVENGERS, GENRE_AVENGERS, LENGTH_AVENGERS);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(movieRepository)
                .updateMovieFromDatabase(MOVIE_ENTITY_AVENGERS);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testUpdateMovieShouldReturnFalseWhenMovieUpdateIsFailedBecauseMovieDoesntExists() {
        // Given
        Mockito.when(movieRepository.updateMovieFromDatabase(MOVIE_ENTITY_AVENGERS))
                .thenReturn(false);

        // When
        boolean actual = underTest.updateMovie(TITLE_AVENGERS, GENRE_AVENGERS, LENGTH_AVENGERS);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(movieRepository)
                .updateMovieFromDatabase(MOVIE_ENTITY_AVENGERS);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }
}
