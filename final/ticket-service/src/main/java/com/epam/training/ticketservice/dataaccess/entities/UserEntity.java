package com.epam.training.ticketservice.dataaccess.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean isAdmin;

    public UserEntity(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
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
        return isAdmin;
    }
}
