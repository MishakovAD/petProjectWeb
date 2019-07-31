package com.project.CinemaTickets.backend.ServerLogic.DAO;


import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Выборка всех кинотеатров по имени или же по адресу.
     * @param cinemaName имя кинотеатра/адрес
     * @param selectFromName - выборка по имени
     * @param selectFromCity - выборка по городу
     * @return список кинотеатров, согласно условиям
     */
    List<Cinema> selectCinema(String cinemaName, boolean selectFromName, boolean selectFromCity);

    /**
     * Выборка кинотеатра согласно его id на кинопоиске.
     * @param cinemaIdFromKinopoisk - id на кинопоиске
     * @return кинотеатр.
     */
    Cinema selectCinema(int cinemaIdFromKinopoisk);

    /**
     * Метод, возвращающий список кино для определенного кинотеатра,
     * либо список всех кинотеатров, в которых идет фильм.
     * @param movieName - в зависимости от флага, либо название фильма, либо parent - отвечает за название кинотеатра.
     * @param selectFromName - true, если хотим искать список кинотеатров по имени фильма
     * @param selectFromParent  - true, если хотим искать список фильмов в кинотеатре
     * @return список фильмов, согласно условию
     */
    List<Movie> selectMovie(String movieName, boolean selectFromName, boolean selectFromParent);

    List<Session> selectSession(Cinema cinema);
    List<Session> selectSession(Movie movie);
    List<Session> selectSession(Movie movie, boolean forTypeAndPrice);

    /**
     * Получаем список сеансов, когда знаем только имя фильма и город пользователя.
     * Т.е. это самый первый запрос при первом обращении пользователя.
     * @param cinemaKinopoiskId - id кинотеатра в кинопоиске (получаем путем выборки всех кинотеатров в указанном городе и с идущим фильмом)
     * @param movieName - название фильма
     * @return список сеансов всех для фильма в указанном городе.
     */
    List<Session> selectSessionListForMovie(String cinemaKinopoiskId, String movieName);

    /**
     * Получаем коллекцию сессия-соответсвующий ей кинотеатр
     * для более удобной фильтрации по месту.
     * @param sessionList - список сеансов, которые интересуют пользователя
     * @return Коллекция (Сессия -> Кинотеатр)
     */
    Map<Session, Cinema> getCinemaWithSessions(List<Session> sessionList);

    /**
     * Возвращает ВСЕ записи с кинотеатрами из БД.
     * @return лист кинотеатров
     */
    List<Cinema> selectAllCinema();

    /**
     * Возвращает ВСЕ записи с фильмами из БД.
     * @return лист фильмов
     */
    List<Movie> selectAllMovie();

    /**
     * Возвращает ВСЕ сеансы из БД.
     * @return лист сеансов
     */
    List<Session> selectAllSession();

}
