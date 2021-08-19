package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.service.RegistrationService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class RegistrationCommandHandler {
    private RegistrationService registrationService;

    public RegistrationCommandHandler(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @ShellMethod(value = "Registration for a non-administrator user", key = "sign up")
    public String registration(String username, String password) {
        boolean isSuccess = registrationService.registNewAccount(username, password);

        return isSuccess ? "Registration was successful!" : "Registration failed! Try again!";
    }
}
