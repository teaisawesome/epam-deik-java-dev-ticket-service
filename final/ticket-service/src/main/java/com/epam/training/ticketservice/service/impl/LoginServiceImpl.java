package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private UserAccount loggedUser = null;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginServiceImpl(UserRepository userRepository, UserAccount loggedUser) {
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public Optional<UserAccount> signIn(String username, String password) {
        Optional<UserAccount> userAccount = userRepository.getUserByUsernameAndPassword(username, password);

        loggedUser = userAccount.orElse(null);

        return Optional.ofNullable(loggedUser);
    }

    @Override
    public Optional<UserAccount> describeLoggedInAccount() {
        if (loggedUser == null) {
            return Optional.empty();
        }

        return Optional.of(loggedUser);
    }

    @Override
    public void signOut() {
        loggedUser = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginServiceImpl that = (LoginServiceImpl) o;
        return Objects.equals(userRepository, that.userRepository) && Objects.equals(loggedUser, that.loggedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepository, loggedUser);
    }

    @Override
    public String toString() {
        return "LoginServiceImpl{" + "userRepository=" + userRepository + ", loggedUser=" + loggedUser + '}';
    }
}
