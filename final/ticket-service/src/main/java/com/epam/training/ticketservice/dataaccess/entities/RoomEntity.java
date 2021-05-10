package com.epam.training.ticketservice.dataaccess.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class RoomEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String name;

    private int chairsRowsNumber;

    private int chairsColsNumber;

    public RoomEntity(String name, int chairsRowsNumber, int chairsColsNumber) {
        this.name = name;
        this.chairsRowsNumber = chairsRowsNumber;
        this.chairsColsNumber = chairsColsNumber;
    }

    public RoomEntity() {

    }

    public void setChairsRowsNumber(int chairsRowsNumber) {
        this.chairsRowsNumber = chairsRowsNumber;
    }

    public void setChairsColsNumber(int chairsColsNumber) {
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
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(name, that.name)
                && Objects.equals(chairsRowsNumber, that.chairsRowsNumber)
                && Objects.equals(chairsColsNumber, that.chairsColsNumber);
    }
}
