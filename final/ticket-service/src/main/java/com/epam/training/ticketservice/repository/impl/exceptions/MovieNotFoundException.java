package com.epam.training.ticketservice.repository.impl.exceptions;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String message) {
        super(message);
    }
}
