package com.epam.training.ticketservice.repository.impl.exceptions;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
