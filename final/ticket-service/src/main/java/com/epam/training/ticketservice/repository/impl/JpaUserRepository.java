package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.entities.UserEntity;
import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private UserDao userDao;

    @Autowired
    public JpaUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<UserAccount> getUserByUsernameAndPassword(String username, String password) {
        Optional<UserEntity> result = userDao.findUserEntityByUsernameAndPassword(username, password);

        if (result.isPresent()) {
            return Optional.of(mapUserEntity(result.get()));
        }

        return Optional.empty();
    }

    private UserAccount mapUserEntity(UserEntity userEntity) {
        return new UserAccount(userEntity.getUsername(), userEntity.getPassword(), userEntity.isAdmin());
    }
}
