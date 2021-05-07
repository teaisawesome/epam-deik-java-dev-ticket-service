package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.entities.UserEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserDatabaseInitializer {

    private UserDao userDao;

    public UserDatabaseInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void initDatabase() {
        UserEntity adminUser = new UserEntity("admin","admin",true);
        userDao.save(adminUser);
    }
}
