package com.epam.training.ticketservice.presentation.cli.handlers;

import com.epam.training.ticketservice.service.LoginService;
import org.springframework.shell.Availability;

public abstract class AbstractAuthenticatedCommand {

    private final LoginService loginService;

    public AbstractAuthenticatedCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    protected Availability loggedIn() {
        return isLoggedIn() ? Availability.available() : Availability.unavailable("You need to login first");
    }

    protected Availability admin() {
        return isLoggedIn() && isAdmin() ? Availability.available() : Availability.unavailable("You are not an admin user");
    }

    private boolean isLoggedIn() {
        return loginService.describeLoggedInAccount().isPresent();
    }

    private boolean isAdmin() {
        return loginService.describeLoggedInAccount().get().isAdminUser();
    }
}
