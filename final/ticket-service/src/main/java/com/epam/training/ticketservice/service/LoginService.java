package com.epam.training.ticketservice.service;


import com.epam.training.ticketservice.domain.user.UserAccount;

import java.util.Optional;

public interface LoginService {
    Optional<UserAccount> signInWithPrivileged(String username, String password);

    Optional<UserAccount>  describeLoggedInAccount();

    void signOut();
}
