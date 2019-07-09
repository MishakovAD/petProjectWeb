package com.project.CinemaTickets.CinemaEntity;

public class Movie {
    private int movieId;
    private String name;
    private String rating;
    private Cinema cinema;
    private Session session;
    private Timetable timetable;
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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public String toString() {
        return "Фильм{" +
                "Название='" + name + '\'' +
                ", Рейтинг='" + rating + '\'' +
                ", Кинотеатр=" + cinema +
                ", Расписание=" + session +
                ", Дата='" + dateFrom + '\'' +
                '}';
    }
}
