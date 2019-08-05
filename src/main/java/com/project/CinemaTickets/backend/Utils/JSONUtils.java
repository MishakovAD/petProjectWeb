package com.project.CinemaTickets.backend.Utils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

//TODO: Сделать отдельными методами преобразование в JSON, Либо такую выгрузку из БД, чтобы сразу выгружалось фильм-кино-сеансы.
public class JSONUtils {
    private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    public static JSONObject parseCinemaToJSON(Cinema cinema) {
        logger.debug("Start method parseCinemaToJSON() at " + LocalDateTime.now());
        JSONObject cinemaJSON = new JSONObject();
        if(cinema.getCinema_id() != 0) {
            cinemaJSON.put("cinemaId", cinema.getCinema_id());
        }
        if (cinema.getCinemaName() != null && !cinema.getCinemaName().isEmpty()) {
            cinemaJSON.put("cinemaName", cinema.getCinemaName());
        }
        if (cinema.getCinemaAddress() != null && !cinema.getCinemaAddress().isEmpty()) {
            cinemaJSON.put("cinemaAddress", cinema.getCinemaAddress());
        }
        if (cinema.getCinemaUnderground() != null && !cinema.getCinemaUnderground().isEmpty()) {
            cinemaJSON.put("cinemaUnderground", cinema.getCinemaUnderground());
        }
        if (cinema.getUrlToAfisha() != null && !cinema.getUrlToAfisha().isEmpty()) {
            cinemaJSON.put("cinemaURLToAfisha", cinema.getUrlToAfisha());
        }
        if (cinema.getUrlToYandexAfisha() != null && !cinema.getUrlToYandexAfisha().isEmpty()) {
            cinemaJSON.put("cinemaURLToYandexAfisha", cinema.getUrlToYandexAfisha());
        }
        if (cinema.getUrlToKinopoisk() != null && !cinema.getUrlToKinopoisk().isEmpty()) {
            cinemaJSON.put("cinemaURLToKinopoisk", cinema.getUrlToKinopoisk());
        }
        if (cinema.getInfoAboutCinema() != null && !cinema.getInfoAboutCinema().isEmpty()) {
            cinemaJSON.put("cinemaInfo", cinema.getInfoAboutCinema());
        }
        logger.debug("End of method parseCinemaToJSON() at " + LocalDateTime.now());
        return cinemaJSON;
    }

    public static JSONObject parseMovieToJSON(Movie movie) {
        logger.debug("Start method parseMovieToJSON() at " + LocalDateTime.now());
        JSONObject movieJSON = new JSONObject();
        if (movie.getMovie_id() != 0) {
            movieJSON.put("movieId", movie.getMovie_id());
        }
        if (movie.getMovieName() != null && !movie.getMovieName().isEmpty()) {
            movieJSON.put("movieName", movie.getMovieName());
        }
        if (movie.getMovieRating() != null && !movie.getMovieRating().isEmpty()) {
            movieJSON.put("movieRating", movie.getMovieRating());
        }
        if (movie.getMovieDate() != null && !movie.getMovieDate().isEmpty()) {
            movieJSON.put("movieDateFrom", movie.getMovieDate());
        }
        logger.debug("End of method parseMovieToJSON() at " + LocalDateTime.now());
        return movieJSON;
    }

    public static JSONObject parseSessionToJSON(Session session) {
        logger.debug("Start method parseMovieToJSON() at " + LocalDateTime.now());
        JSONObject sessionJSON = new JSONObject();
        if (session.getSession_id() != 0) {
            sessionJSON.put("sessionId", session.getSession_id());
        }
        if (session.getTimeOfShow() != null && !session.getTimeOfShow().isEmpty()) {
            sessionJSON.put("sessionTime", session.getTimeOfShow());
        }
        if (session.getTypeOfShow() != null && !session.getTypeOfShow().isEmpty()) {
            sessionJSON.put("sessionType", session.getTypeOfShow());
        }
        if (session.getPrice() != null && !session.getPrice().isEmpty()) {
            sessionJSON.put("sessionPrice", session.getPrice());
        }
        if (session.getUrl() != null && !session.getUrl().isEmpty()) {
            sessionJSON.put("sessionUrl", session.getUrl());
        }
        logger.debug("End of method parseMovieToJSON() at " + LocalDateTime.now());
        return sessionJSON;
    }

    //TODO: разделить на 3 разных метода
    public static JSONObject parseCinemaToJSONFromInternet(Cinema cinema) {
        logger.debug("Start method parseCinemaToJSONFromInternet() at " + LocalDateTime.now());
        JSONObject cinemaJSON = new JSONObject();
        if(cinema.getCinema_id() != 0) {
            cinemaJSON.put("cinemaId", cinema.getCinema_id());
        }
        if (cinema.getCinemaName() != null && !cinema.getCinemaName().isEmpty()) {
            cinemaJSON.put("cinemaName", cinema.getCinemaName());
        }
        if (cinema.getCinemaAddress() != null && !cinema.getCinemaAddress().isEmpty()) {
            cinemaJSON.put("cinemaAddress", cinema.getCinemaAddress());
        }
        if (cinema.getCinemaUnderground() != null && !cinema.getCinemaUnderground().isEmpty()) {
            cinemaJSON.put("cinemaUnderground", cinema.getCinemaUnderground());
        }
        if (cinema.getUrlToAfisha() != null && !cinema.getUrlToAfisha().isEmpty()) {
            cinemaJSON.put("cinemaURLToAfisha", cinema.getUrlToAfisha());
        }
        if (cinema.getUrlToYandexAfisha() != null && !cinema.getUrlToYandexAfisha().isEmpty()) {
            cinemaJSON.put("cinemaURLToYandexAfisha", cinema.getUrlToYandexAfisha());
        }
        if (cinema.getUrlToKinopoisk() != null && !cinema.getUrlToKinopoisk().isEmpty()) {
            cinemaJSON.put("cinemaURLToKinopoisk", cinema.getUrlToKinopoisk());
        }
        if (cinema.getInfoAboutCinema() != null && !cinema.getInfoAboutCinema().isEmpty()) {
            cinemaJSON.put("cinemaInfo", cinema.getInfoAboutCinema());
        }

        JSONArray movieListJSON = new JSONArray();
        JSONObject movieJSON;
        List<Movie> movieList = cinema.getMovieList();
        if (movieList != null && movieList.size() > 0) {
            for (Movie movie : movieList) {
                movieJSON = new JSONObject();
                if (movie.getMovie_id() != 0) {
                    movieJSON.put("movieId", movie.getMovie_id());
                }
                if (movie.getMovieName() != null && !movie.getMovieName().isEmpty()) {
                    movieJSON.put("movieName", movie.getMovieName());
                }
                if (movie.getMovieRating() != null && !movie.getMovieRating().isEmpty()) {
                    movieJSON.put("movieRating", movie.getMovieRating());
                }
                if (movie.getMovieDate() != null && !movie.getMovieDate().isEmpty()) {
                    movieJSON.put("movieDateFrom", movie.getMovieDate());
                }

                JSONArray sessionListJSON = new JSONArray();
                JSONObject sessionJSON;
                List<Session> sessionList = movie.getSessionList();
                if (sessionList != null && sessionList.size() > 0) {
                    for (Session session : sessionList) {
                        sessionJSON = new JSONObject();
                        if (session.getSession_id() != 0) {
                            sessionJSON.put("sessionId", session.getSession_id());
                        }
                        if (session.getTimeOfShow() != null && !session.getTimeOfShow().isEmpty()) {
                            sessionJSON.put("sessionTime", session.getTimeOfShow());
                        }
                        if (session.getTypeOfShow() != null && !session.getTypeOfShow().isEmpty()) {
                            sessionJSON.put("sessionType", session.getTypeOfShow());
                        }
                        if (session.getPrice() != null && !session.getPrice().isEmpty()) {
                            sessionJSON.put("sessionPrice", session.getPrice());
                        }
                        if (session.getUrl() != null && !session.getUrl().isEmpty()) {
                            sessionJSON.put("sessionUrl", session.getUrl());
                        }
                        sessionListJSON.put(sessionJSON);
                    }
                    movieJSON.put("session", sessionListJSON);
                } else {
                    Session session = movie.getSession();
                    sessionJSON = new JSONObject();
                    if (session.getSession_id() != 0) {
                        sessionJSON.put("sessionId", session.getSession_id());
                    }
                    if (session.getTimeOfShow() != null && !session.getTimeOfShow().isEmpty()) {
                        sessionJSON.put("sessionTime", session.getTimeOfShow());
                    }
                    if (session.getTypeOfShow() != null && !session.getTypeOfShow().isEmpty()) {
                        sessionJSON.put("sessionType", session.getTypeOfShow());
                    }
                    if (session.getPrice() != null && !session.getPrice().isEmpty()) {
                        sessionJSON.put("sessionPrice", session.getPrice());
                    }
                    if (session.getUrl() != null && !session.getUrl().isEmpty()) {
                        sessionJSON.put("sessionUrl", session.getUrl());
                    }
                    movieJSON.put("session", sessionJSON);
                }

                movieListJSON.put(movieJSON);
            }
        }


        cinemaJSON.put("movieList", movieListJSON);
        logger.debug("End of method parseCinemaToJSONFromInternet() at " + LocalDateTime.now());
        return cinemaJSON;
    }
}
