package com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema_Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;

import java.util.List;

/**
 * Класс, предназначенный для взаимодействия с БД с помощью Hibernate.
 */
public interface HibernateDao {

    /**
     * Сохраняет в БД все дерево (кинотеатр-фильмы-сеансы)
     * без повторяющихся значений.
     * @param cinemaMovieSessionList - список объектов (кинотеатр-кино-сеанс)
     * @return true, если сохранение прошло успешно
     */
    boolean saveCinemaMovieSessionObj(List<CinemaMovieSession> cinemaMovieSessionList);

    /**
     * Выбор всех кинотеатров из БД.
     * @return список кинотеатров
     */
    List<Cinema> selectAllCinema();

    /**
     * Выбор всех Кино из БД.
     * @return список фильмов
     */
    List<Movie> selectAllMovie();

    /**
     * Выбор всех сеансов из БД.
     * @return список фильмов
     */
    List<Session> selectAllSession();

    /**
     * Получаем все объекты Cinema_Movie из таблицы ManyToMany.
     * @return список Cinema_Movie
     */
    List<Cinema_Movie> selectAllCinema_Movie();

    /**
     * Метод возвращает список сеансов для выбранного фильма.
     * @param movie - фильм
     * @return список сеансов
     */
    List<Session> selectSessionsForMovie(Movie movie);
}
