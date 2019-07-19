package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;

import java.util.List;

public interface PliUserLogicFromDB {

    /**
     * Метод, который получает список кинотеатров из БД,
     * согласно запросу пользователя. Если кинотеатров нет,
     * то необходимо искать в интернете.
     * @param movieName - навзание фильма
     * @param time - время сеанса
     * @param type - тип сеанса
     * @param place - ближайшее место, адрес, метро
     * @return - список кинотеатров, удовлетворяющих запросу
     */
    public List<Cinema> getCinemasListForUserFromDB (String movieName, String time, String type, String place);

    /**
     * Метод, который получает полное расписание всех фильмов
     * для выбранного кинотеатра.
     * @param cinemaId - ID киноеатра
     * @param date - дата, для которой нужно получить расписание фильмов в кинотеатре
     * @return объект Cinema с заполненным расписанием
     */
    public Cinema getCinemaFromDB(int cinemaId, String date);

    /**
     * Метод, который получает полное расписание всех фильмов
     * для выбранного кинотеатра.
     * @param cinemaName - название кинотеатра
     * @param date - дата, для которой нужно получить расписание фильмов в кинотеатре
     * @return объект Cinema с заполненным расписанием
     */
    public Cinema getCinemaFromDB(String cinemaName, String date);
}
