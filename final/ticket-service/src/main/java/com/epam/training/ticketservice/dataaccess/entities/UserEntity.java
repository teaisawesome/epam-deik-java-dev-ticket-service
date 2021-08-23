package com.epam.training.ticketservice.dataaccess.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private boolean adminAccount;

    public UserEntity(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.adminAccount = isAdmin;
    }

    public UserEntity() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return adminAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(adminAccount, that.adminAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, adminAccount);
    }

    @Override
    public String toString() {
        return "UserEntity{" + "username=" + username + "password=" + password + ", isAdmin=" + adminAccount + '}';
    }
}
