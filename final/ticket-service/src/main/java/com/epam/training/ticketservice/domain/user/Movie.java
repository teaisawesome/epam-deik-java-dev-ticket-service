package com.epam.training.ticketservice.domain.user;

import java.util.Objects;

public class Movie {
    private final String title;

    private final String genre;

    private final int length;

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
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
        Movie that = (Movie) o;
        return Objects.equals(title, that.title)
                && Objects.equals(genre, that.genre)
                && Objects.equals(length, that.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, length);
    }

    @Override
    public String toString() {
        return "Movie{" + "title=" + title + "genre=" + genre + ", length=" + length + '}';
    }
}
