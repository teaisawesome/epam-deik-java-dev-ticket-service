package com.epam.training.ticketservice.domain.user;

import java.util.Date;
import java.util.Objects;

public class Screening {
    private final Movie movieOnScreen;

    private final Room screeningRoom;

    private final Date screeningTime;


    public Screening(Movie movieOnScreen, Room screeningRoom, Date screeningTime) {
        this.movieOnScreen = movieOnScreen;
        this.screeningRoom = screeningRoom;
        this.screeningTime = screeningTime;
    }

    public Movie getMovieOnScreen() {
        return movieOnScreen;
    }

    public Room getScreeningRoom() {
        return screeningRoom;
    }

    public Date getScreeningTime() {
        return screeningTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screening that = (Screening) o;
        return Objects.equals(movieOnScreen, that.movieOnScreen)
                && Objects.equals(screeningRoom, that.screeningRoom)
                && Objects.equals(screeningTime, that.screeningTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieOnScreen, screeningRoom, screeningTime);
    }
}
