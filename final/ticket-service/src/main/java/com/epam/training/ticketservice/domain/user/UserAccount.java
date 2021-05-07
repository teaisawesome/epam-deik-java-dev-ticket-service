package com.epam.training.ticketservice.domain.user;

public class UserAccount implements User {

    private final String username;

    private final String password;

    private final boolean isAdmin;

    public UserAccount(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean isAdminUser() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
