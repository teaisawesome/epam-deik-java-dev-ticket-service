package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.domain.user.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl  implements RegistrationService {
    private UserRepository userRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public boolean registNewAccount(String username, String password) {
        return userRepository.createNonAdminAccount(username, password);
    }
}
