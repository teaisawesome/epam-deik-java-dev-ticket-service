package com.epam.training.ticketservice.dataaccess;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.entities.UserEntity;
import com.epam.training.ticketservice.dataaccess.init.UserDatabaseInitializer;
import com.epam.training.ticketservice.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserDatabaseInitializerTest {

    private UserDao userDao;

    private UserDatabaseInitializer underTest;

    @BeforeEach
    public void init() {
        userDao = Mockito.mock(UserDao.class);
        underTest = new UserDatabaseInitializer(userDao);
    }

    @Test
    public void testInitDatabaseShouldSaveAdminUser() {
        // Given
        UserEntity adminUser = new UserEntity("admin","admin",true);

        // When
        underTest.initDatabase();

        // Then
        Mockito.verify(userDao).save(adminUser);
    }
}
