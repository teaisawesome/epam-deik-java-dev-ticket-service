package com.epam.training.ticketservice.dataaccess.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ScreeningEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private MovieEntity movie;

    @ManyToOne
    private RoomEntity room;

    private Date startTime;

    public ScreeningEntity(MovieEntity movie, RoomEntity room, Date startTime) {
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
    }

    public ScreeningEntity() {

    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScreeningEntity that = (ScreeningEntity) o;
        return Objects.equals(movie, that.movie)
                && Objects.equals(room, that.room)
                && Objects.equals(startTime, that.startTime);
    }
}
