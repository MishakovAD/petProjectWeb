package com.project.CinemaTickets.backend.ServerLogic.DAO.DAOHelperUtils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;

import java.util.List;

/**
 * Вспомогательный класс, который предназначен для подготовки списка элементов для вставки в БД.
 */
public interface ConverterTo {

    /**
     * Метод, преобразуещий список кинотеатров (с заполненными фильмами и сеансами)
     * в список специальных объектов CinemaMovieSession.
     * @param cinemaList - заполенный список кинотеатров
     * @return список объектов для БД
     */
    List<CinemaMovieSession> getCinemaMovieSessionListCinemasList(List<Cinema> cinemaList);
}
