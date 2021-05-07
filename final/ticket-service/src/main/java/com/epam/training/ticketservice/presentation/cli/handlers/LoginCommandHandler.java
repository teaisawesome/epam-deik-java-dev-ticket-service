package com.epam.training.ticketservice.presentation.cli.handlers;


import com.epam.training.ticketservice.service.LoginService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LoginCommandHandler {

    private LoginService loginService;

    public LoginCommandHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = "Login as Admin User", key = "sign in privileged")
    public String login(String username, String password) {
        return loginService.signIn(username, password);
    }
}
