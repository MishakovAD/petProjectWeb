package com.project.CinemaTickets.backend.Utils;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.CinemaEntity.Session;
import org.json.JSONArray;
import org.json.JSONObject;


public class JSONUtils {
    public static JSONObject parseCinemaToJSON(Cinema cinema) {
        JSONObject cinemaJSON = new JSONObject();
        if(cinema.getCinemaId() != 0) {
            cinemaJSON.put("cinemaId", cinema.getCinemaId());
        }
        if (cinema.getName() != null && !cinema.getName().isEmpty()) {
            cinemaJSON.put("cinemaName", cinema.getName());
        }
        if (cinema.getAddress() != null && !cinema.getAddress().isEmpty()) {
            cinemaJSON.put("cinemaAddress", cinema.getAddress());
        }
        if (cinema.getUnderground() != null && !cinema.getUnderground().isEmpty()) {
            cinemaJSON.put("cinemaUnderground", cinema.getUnderground());
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
        for (Movie movie : cinema.getMovieList()) {
            movieJSON = new JSONObject();
            if (movie.getMovieId() != 0) {
                movieJSON.put("movieId", movie.getMovieId());
            }
            if (movie.getName() != null && !movie.getName().isEmpty()) {
                movieJSON.put("movieName", movie.getName());
            }
            if (movie.getRating() != null && !movie.getRating().isEmpty()) {
                movieJSON.put("movieRating", movie.getRating());
            }
            if (movie.getDateFrom() != null && !movie.getDateFrom().isEmpty()) {
                movieJSON.put("movieDateFrom", movie.getDateFrom());
            }

            JSONObject sessionJSON = new JSONObject();
            Session session = movie.getSession();

            if (session.getSessionId() != 0) {
                sessionJSON.put("sessionId", session.getSessionId());
            }
            if (session.getTimeOfShow() != null && !session.getTimeOfShow().isEmpty()) {
                sessionJSON.put("sessionTime", session.getTimeOfShow());
            }
            if (session.getTypeOfMovie() != null && !session.getTypeOfMovie().isEmpty()) {
                sessionJSON.put("sessionType", session.getTypeOfMovie());
            }
            if (session.getPrice() != null && !session.getPrice().isEmpty()) {
                sessionJSON.put("sessionPrice", session.getPrice());
            }
            if (session.getUrl() != null && !session.getUrl().isEmpty()) {
                sessionJSON.put("sessionUrl", session.getUrl());
            }

            movieJSON.put("session", sessionJSON);
            movieListJSON.put(movieJSON);
        }

        cinemaJSON.put("movieList", movieListJSON);
        System.out.println(cinemaJSON.toString());
        return cinemaJSON;
    }
}
