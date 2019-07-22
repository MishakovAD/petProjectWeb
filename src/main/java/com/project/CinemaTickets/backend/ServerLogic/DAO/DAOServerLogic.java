package com.project.CinemaTickets.backend.ServerLogic.DAO;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.CinemaEntity.Session;

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
     * @return true, если добавление прошло успешно.
     */
    boolean insertMovieToDB(Movie movie);

    /**
     * Добавляем сессию в базу данных.
     * @param session - Объект сессии
     * @return true, если добавление прошло успешно.
     */
    boolean insertSessionToDB(Session session);

    boolean updateCinema(Cinema cinema);
    boolean updateMovie(Movie movie);
    boolean updateSession(Session session);

    boolean removeCinema(Cinema cinema);
    boolean removeMovie(Movie movie);
    boolean removeSession(Session session);

    boolean selectCinema(String cinemaName);
    boolean selectCinema(int cinemaId);

    boolean selectMovie(String movieName);
    boolean selectMovie(int movieId);

    boolean selectSession(Cinema cinema);
    boolean selectSession(Movie movie);

}
