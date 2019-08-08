package com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;

public interface CinemaMovieSession<C extends Cinema, M extends Movie, S extends Session> {

    C getCinema();

    void setCinema(C cinema);

    M getMovie();

    void setMovie(M movie);

    S getSession();

    void setSession(S session);
}
