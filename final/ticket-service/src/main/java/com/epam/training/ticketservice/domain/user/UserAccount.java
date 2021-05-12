package com.epam.training.ticketservice.domain.user;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccount that = (UserAccount) o;
        return Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(isAdmin, that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isAdmin);
    }

    @Override
    public String toString() {
        return "UserAccount{" + "username=" + username + "password=" + password + ", isAdmin=" + isAdmin + '}';
    }
}
