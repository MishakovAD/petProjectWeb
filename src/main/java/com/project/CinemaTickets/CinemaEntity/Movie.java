package com.project.CinemaTickets.CinemaEntity;

public class Movie {
    private String name;
    private String rating;
    private Cinema cinema;
    private Session session;
    private String dateFrom;

    public Movie() {
    }

    public Movie(String name, String rating, Cinema cinema, Session session, String dateFrom) {
        this.name = name;
        this.rating = rating;
        this.cinema = cinema;
        this.session = session;
        this.dateFrom = dateFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", cinema=" + cinema +
                ", session=" + session +
                ", dateFrom='" + dateFrom + '\'' +
                '}';
    }
}
