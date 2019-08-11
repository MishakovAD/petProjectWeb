package com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils;

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
}
