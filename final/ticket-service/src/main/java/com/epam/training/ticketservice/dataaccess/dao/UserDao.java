package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByUsernameAndPasswordAndAdminAccount(String username, String password, boolean admin);

    boolean existsByUsername(String username);
}
