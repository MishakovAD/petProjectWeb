package com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;

public class CinemaMovieSessionObj<C extends Cinema, M extends Movie, S extends Session> implements CinemaMovieSession {
    private Cinema cinema;
    private Movie movie;
    private Session session;


    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
