package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.entities.UserEntity;
import com.epam.training.ticketservice.domain.user.User;
import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.repository.impl.JpaUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class JpaUserRepositoryTest {
    private UserDao userDao;

    private JpaUserRepository underTest;

    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    @BeforeEach
    public void init() {
        userDao = Mockito.mock(UserDao.class);
        underTest = new JpaUserRepository(userDao);
    }

    @Test
    public void testGetUserByUsernameAndPasswordShouldReturnOptionalEmptyWhenCannotFindUserWithGivenUsernameAndPassword() {
        // Given
        Optional<UserAccount> expected = Optional.empty();
        Mockito.when(userDao.findUserEntityByUsernameAndPassword("bob","bob"))
                .thenReturn(Optional.empty());

        // When
        Optional<UserAccount> actual = underTest.getUserByUsernameAndPassword("bob","bob");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(userDao).findUserEntityByUsernameAndPassword("bob","bob");
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testGetUserByUsernameAndPasswordShouldReturnUserAccountWhenUserFindWithGivenUsernameAndPassword() {
        // Given
        UserEntity userEntity = new UserEntity(USERNAME, PASSWORD,true);
        Mockito.when(userDao.findUserEntityByUsernameAndPassword(USERNAME,PASSWORD))
                .thenReturn(Optional.of(userEntity));
        UserAccount userAccount = new UserAccount(USERNAME, PASSWORD, true);
        Optional<UserAccount> expected = Optional.of(userAccount);

        // When
        Optional<UserAccount> actual = underTest.getUserByUsernameAndPassword(USERNAME, PASSWORD);

        // Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(new JpaUserRepository(userDao), underTest);
    }
}
