package com.epam.training.ticketservice.service;


import com.epam.training.ticketservice.domain.user.UserAccount;

import java.util.Optional;

public interface LoginService {
    String signIn(String username, String password);

    String describeLoggedInAccount();

    void signOut();

    Optional<UserAccount> getLoggedInUser();
}
