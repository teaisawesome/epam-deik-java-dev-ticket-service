package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.user.UserAccount;

import java.util.Optional;

public interface UserRepository {
    Optional<UserAccount> getUserByUsernameAndPassword(String username, String password);
}