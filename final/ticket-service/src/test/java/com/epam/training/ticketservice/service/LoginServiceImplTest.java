package com.epam.training.ticketservice.service;


import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class LoginServiceImplTest
{
    private UserRepository userRepository;

    private LoginServiceImpl underTest;

    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";


    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        underTest = new LoginServiceImpl(userRepository);
    }

    @Test
    public void testSignInShoudReturnLoginSuccessOnAdminLogin() {
        // Given
        UserAccount expectedUserAccount = new UserAccount("admin", "admin", true);
        Optional<UserAccount> expected = Optional.of(expectedUserAccount);
        Mockito.when(userRepository.getAdminByUsernameAndPassword(USERNAME, PASSWORD))
                .thenReturn(Optional.of(expectedUserAccount));

        // When
        Optional<UserAccount> actual = underTest.signIn(USERNAME, PASSWORD);

        // Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(new LoginServiceImpl(userRepository, expectedUserAccount), underTest);
        Mockito.verify(userRepository).getAdminByUsernameAndPassword(USERNAME, PASSWORD);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSignInShouldReturnOptionalEmptyWhenUsernameAndPasswordAreIncorrect() {
        // Given
        Optional<UserAccount> expected = Optional.empty();
        Mockito.when(userRepository.getAdminByUsernameAndPassword("kakao", "kakao"))
                .thenReturn(Optional.empty());

        // When
        Optional<UserAccount> actual = underTest.signIn("kakao", "kakao");

        // Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(new LoginServiceImpl(userRepository, null), underTest);
        Mockito.verify(userRepository).getAdminByUsernameAndPassword("kakao", "kakao");
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testDescribeLoggedInAccountShouldReturnOptionalEmptyWhenLoggedUserIsNull() {
        // Given
        Optional<UserAccount> expected = Optional.empty();

        // When
        Optional<UserAccount> actual = underTest.describeLoggedInAccount();

        // Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(new LoginServiceImpl(userRepository, null), underTest);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testDescribeLoggedInAccountShouldReturnTheLoggedUserWhenLoggedUserIsSet() {
        // Given
        UserAccount expectedUserAccount = new UserAccount("admin", "admin", true);
        underTest = new LoginServiceImpl(userRepository, expectedUserAccount);
        Optional<UserAccount> expected = Optional.of(expectedUserAccount);

        // When
        Optional<UserAccount> actual = underTest.describeLoggedInAccount();

        // Then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(new LoginServiceImpl(userRepository, expectedUserAccount), underTest);
    }

    @Test
    public void testSignOutShouldSetLoggedUserAsNull() {
        //Given
        UserAccount expectedUserAccount = new UserAccount("admin", "admin", true);
        underTest = new LoginServiceImpl(userRepository, expectedUserAccount);

        // When
        underTest.signOut();

        // Then
        Assertions.assertEquals(new LoginServiceImpl(userRepository, null), underTest);
    }
}
