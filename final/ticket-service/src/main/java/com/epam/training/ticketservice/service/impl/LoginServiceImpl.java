package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private UserAccount loggedUser = null;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String signIn(String username, String password) {
        Optional<UserAccount> userAccount = userRepository.getUserByUsernameAndPassword(username, password);

        if (userAccount.isPresent()) {
            loggedUser = userAccount.get();
            return "Login success";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    @Override
    public String describeLoggedInAccount() {
        if (loggedUser == null) {
            return "You are not signed in";
        }

        if (loggedUser.isAdminUser()) {
            return "Signed in with privileged account '" + loggedUser.getUsername() + "'";
        }

        return "Signed in with account '" + loggedUser.getUsername() + "'";
    }

    @Override
    public void signOut() {
        loggedUser = null;
    }

    @Override
    public Optional<UserAccount> getLoggedInUser() {
        return Optional.ofNullable(loggedUser);
    }
}
