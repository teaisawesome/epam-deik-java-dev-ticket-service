package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.entities.MovieEntity;
import com.epam.training.ticketservice.domain.user.Movie;
import com.epam.training.ticketservice.repository.impl.JpaMovieRepository;
import com.epam.training.ticketservice.repository.impl.JpaUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class JpaMovieRepositoryTest {

    private MovieDao movieDao;
    private JpaMovieRepository underTest;

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
        movieDao = Mockito.mock(MovieDao.class);
        underTest = new JpaMovieRepository(movieDao);
    }

    @Test
    public void testSaveMovieToDatabaseShouldReturnTrueWhenGivenMovieDoesntExists() {
        // Given
        Mockito.when(movieDao.existsByTitle(TITLE_ALIEN))
                .thenReturn(false);
        Mockito.when(movieDao.save(MOVIE_ENTITY_ALIEN))
                .thenReturn(MOVIE_ENTITY_ALIEN);

        // When
        boolean actual = underTest.saveMovieToDatabase(MOVIE_ENTITY_ALIEN);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(movieDao).existsByTitle(TITLE_ALIEN);
        Mockito.verify(movieDao).save(MOVIE_ENTITY_ALIEN);
        Mockito.verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void testSaveMovieToDatabaseShouldReturnFalseWhenGivenMovieAlreadyExists() {
        // Given
        Mockito.when(movieDao.existsByTitle(TITLE_ALIEN))
                .thenReturn(true);

        // When
        boolean actual = underTest.saveMovieToDatabase(MOVIE_ENTITY_ALIEN);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(movieDao).existsByTitle(TITLE_ALIEN);
        Mockito.verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void testDeleteMovieFromDatabaseShouldReturnTrueAndDeleteMovieWhenMovieAlreadyExists() {
        // Given
        Mockito.when(movieDao.existsByTitle(TITLE_AVENGERS))
                .thenReturn(true);

        // When
        boolean actual = underTest.deleteMovieFromDatabase(TITLE_AVENGERS);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(movieDao).existsByTitle(TITLE_AVENGERS);
        Mockito.verify(movieDao).deleteByTitle(TITLE_AVENGERS);
        Mockito.verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void testDeleteMovieFromDatabaseShouldReturnFalseAndDeletionFailedWhenMovieDoesntExists() {
        // Given
        Mockito.when(movieDao.existsByTitle(TITLE_AVENGERS))
                .thenReturn(false);

        // When
        boolean actual = underTest.deleteMovieFromDatabase(TITLE_AVENGERS);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(movieDao).existsByTitle(TITLE_AVENGERS);
        Mockito.verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void testFindAllMoviesShouldReturnMovieEntityList() {
        // Given
        List<MovieEntity> expected = new ArrayList<>();
        expected.add(MOVIE_ENTITY_ALIEN);
        expected.add(MOVIE_ENTITY_AVENGERS);
        Mockito.when(movieDao.findAll())
                .thenReturn(expected);

        // When
        List<MovieEntity> actual = underTest.findAllMovies();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieDao).findAll();
        Mockito.verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void testUpdateMovieFromDatabaseShouldReturnTrueWhenMovieUpdateIsComplete() {
        // Given
        MovieEntity movieEntity = new MovieEntity(TITLE_AVENGERS, GENRE_ALIEN, LENGTH_ALIEN);
        Mockito.when(movieDao.existsByTitle(TITLE_AVENGERS))
                .thenReturn(true);
        Mockito.when(movieDao.getMovieEntityByTitle(TITLE_AVENGERS))
                .thenReturn(MOVIE_ENTITY_AVENGERS);
        Mockito.when(movieDao.save(movieEntity))
                .thenReturn(movieEntity);

        // When
        boolean actual = underTest.updateMovieFromDatabase(movieEntity);

        // Then
        Assertions.assertTrue(actual);
        Mockito.verify(movieDao).existsByTitle(TITLE_AVENGERS);
        Mockito.verify(movieDao).getMovieEntityByTitle(TITLE_AVENGERS);
        Mockito.verify(movieDao).save(movieEntity);
        Mockito.verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void testUpdateMovieFromDatabaseShouldReturnFalseWhenMovieUpdateIsFailedBecauseMoveDoesntExists() {
        // Given
        MovieEntity movieEntity = new MovieEntity(TITLE_AVENGERS, GENRE_ALIEN, LENGTH_ALIEN);
        Mockito.when(movieDao.existsByTitle(TITLE_AVENGERS))
                .thenReturn(false);

        // When
        boolean actual = underTest.updateMovieFromDatabase(movieEntity);

        // Then
        Assertions.assertFalse(actual);
        Mockito.verify(movieDao).existsByTitle(TITLE_AVENGERS);
        Mockito.verifyNoMoreInteractions(movieDao);
    }
}
