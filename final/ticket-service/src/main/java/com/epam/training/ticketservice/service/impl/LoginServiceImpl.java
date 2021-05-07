package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.domain.user.UserAccount;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String signIn(String username, String password) {
        Optional<UserAccount> userAccount = userRepository.getUserByUsernameAndPassword(username, password);

        if (userAccount.isPresent()) {
            return "Login success" + userAccount.get().getUsername() + " " + userAccount.get().getPassword();
        }
        else
        {
            return "Login failed due to incorrect credentials";
        }
    }
}
