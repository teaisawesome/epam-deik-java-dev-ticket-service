package com.epam.training.ticketservice.domain.user;

import java.util.Objects;

public class Room {
    private final String name;

    private final int chairsRowsNumber;

    private final int chairsColsNumber;


    public Room(String name, int chairsRowsNumber, int chairsColsNumber) {
        this.name = name;
        this.chairsRowsNumber = chairsRowsNumber;
        this.chairsColsNumber = chairsColsNumber;
    }

    public String getName() {
        return name;
    }

    public int getChairsRowsNumber() {
        return chairsRowsNumber;
    }

    public int getChairsColsNumber() {
        return chairsColsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room that = (Room) o;
        return Objects.equals(name, that.name)
                && Objects.equals(chairsRowsNumber, that.chairsRowsNumber)
                && Objects.equals(chairsColsNumber, that.chairsColsNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, chairsRowsNumber, chairsColsNumber);
    }
}
