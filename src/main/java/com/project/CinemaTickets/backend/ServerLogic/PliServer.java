package com.project.CinemaTickets.backend.ServerLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;

import java.io.IOException;
import java.util.List;

public interface PliServer {

    /**
     * Получение информации обо всех кинотеатрах, которые есть на сайте
     * вместе со всем расписание каждого фильма.
     * @param date - дата, на которую интересует расписание
     *             (параметр передается для того, чтобы
     *             можно было удаленно вызвать метод)
     * @return список кинотеатров, заполненный данными (каскадно можешь добраться до всего)
     */
    public List<Cinema> getAllCinemasFromKinopoisk(String date) throws IOException;

    /**
     * Метод, который получает полное расписание всех фильмов
     * для выбранного кинотеатра.
     * @param cinemaId - ID киноеатра, для того, чтобы вставить в ссылку
     * @param date - дата, для которой нужно получить расписание фильмов в кинотеатре
     * @return объект Cinema с заполненным расписанием
     */
    public Cinema getCinemaForDBFromKinopoisk(int cinemaId, String date);

    /**
     * Метод, который эмулирует деятельность человека
     * автоматически переходит по ссылкам и ищет
     * различный контент. Тестовый метод.
     */
    public void emulationHumanActivity() throws IOException;

}
