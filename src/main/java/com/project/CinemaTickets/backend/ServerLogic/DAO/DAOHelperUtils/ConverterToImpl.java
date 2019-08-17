package com.project.CinemaTickets.backend.ServerLogic.DAO.DAOHelperUtils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSessionObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.project.CinemaTickets.backend.Utils.HelperUtils.createUniqueID;

public class ConverterToImpl implements ConverterTo {
    public static Pattern PATTERN_URL_BUY_TICKETS = Pattern.compile("https://tickets.widget.kinopoisk.ru/w/sessions/");
    private Logger logger = LoggerFactory.getLogger(ConverterToImpl.class);

    @Override
    public List<CinemaMovieSession> getCinemaMovieSessionListCinemasList(List<Cinema> cinemaList) {
        logger.info("Start method getCinemaMovieSessionListCinemasList() at " + LocalDateTime.now());
        long startTime = System.currentTimeMillis();
        List<CinemaMovieSession> cinemaMovieSessionList = new LinkedList<>();
        Map<String, Movie> movieNameMap = new HashMap<>();

        cinemaList.forEach( cinema -> {
            String address = cinema.getCinemaAddress();
            if (address != null && !address.isEmpty() && !address.equals("")){
                String city = address.substring(3, address.indexOf(",", 1));
                cinema.setCinemaCity(city);
            }
            String cinemaId = createUniqueID();
            cinema.setCinema_id(cinemaId);
            cinema.getMovieList().forEach( movie -> {
                String movieName = movie.getMovieName();
                if (!movieNameMap.keySet().contains(movieName)) {
                    String movieId = createUniqueID();
                    movie.setMovie_id(movieId);
                    movieNameMap.put(movieName, movie);
                }

                movie.getSessionList().forEach( session -> {
                    String sessionId = createUniqueID();
                    session.setSession_id(sessionId);
                    session.setCinema_id(cinemaId);
                    String uniqueMovieId = movieNameMap.get(movieName).getMovie_id();
                    session.setMovie_id(uniqueMovieId);
                    if (PATTERN_URL_BUY_TICKETS.matcher(session.getUrl()).matches()) {
                        StringBuilder url = new StringBuilder(session.getUrl());
                        url.append("NO-ONLINE-TICKETS-" + createUniqueID());
                        session.setUrl(url.toString());
                    }

                    CinemaMovieSession cmsObj = new CinemaMovieSessionObj();
                    cmsObj.setCinema(cinema);
                    cmsObj.setMovie(movie);
                    cmsObj.setSession(session);
                    cinemaMovieSessionList.add(cmsObj);
                });
            });
        });
        long endTime = System.currentTimeMillis();
        logger.info("End of method getCinemaMovieSessionListCinemasList() at " + LocalDateTime.now() + " - with work time = " + (endTime - startTime)/1000 + "s.");
        return cinemaMovieSessionList;
    }

    public static void main(String[] args) {
        System.out.println(PATTERN_URL_BUY_TICKETS.matcher("https://tickets.widget.kinopoisk.ru/w/sessions/").matches());
    }
}
