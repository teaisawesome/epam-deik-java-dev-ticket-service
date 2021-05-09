package com.epam.training.ticketservice.dataaccess.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MovieEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String title;

    private String genre;

    private int length;


    public MovieEntity(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public MovieEntity() {

    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(title, that.title)
                && Objects.equals(genre, that.genre)
                && Objects.equals(length, that.length);
    }
}
