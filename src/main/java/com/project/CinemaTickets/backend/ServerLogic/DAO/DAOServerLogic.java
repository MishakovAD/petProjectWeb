package com.project.CinemaTickets.backend.ServerLogic.DAO;


import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;

import java.util.List;

public interface DAOServerLogic {

    /**
     * Добавляем кинотеатр в базу данных.
     * (Все дерево: кинотеатр -> фильмы -> сеансы)
     * @param cinema - Объект кинотеатра
     * @return true, если добавление прошло успешно.
     */
    boolean insertCinemaToDB(Cinema cinema);

    /**
     * Добавляем кино в базу данных.
     * @param movie - Объект кино
     * @param cinema - Объект кинотеатра
     * @return true, если добавление прошло успешно.
     */
    boolean insertMovieToDB(Cinema cinema, Movie movie);

    /**
     * Добавляем сессию в базу данных.
     * @param session - Объект сессии
     * @param movie - Объект кино
     * @param cinema - Объект кинотеатра
     * @return true, если добавление прошло успешно.
     */
    boolean insertSessionToDB(Cinema cinema, Movie movie, Session session);

    boolean updateCinema(Cinema cinema);
    boolean updateMovie(Movie movie);
    boolean updateSession(Session session);

    boolean removeCinema(Cinema cinema);
    boolean removeMovie(Movie movie);
    boolean removeSession(Session session);

    Cinema selectCinema(String cinemaName);
    Cinema selectCinema(int cinemaIdFromKinopoisk);

    List<Movie> selectMovie(String movieName, boolean selectFromName, boolean selectFromParent);

    List<Session> selectSession(Cinema cinema);
    List<Session> selectSession(Movie movie);

    List<Cinema> selectAllCinema();
    List<Movie> selectAllMovie();
    List<Session> selectAllSession();

}
