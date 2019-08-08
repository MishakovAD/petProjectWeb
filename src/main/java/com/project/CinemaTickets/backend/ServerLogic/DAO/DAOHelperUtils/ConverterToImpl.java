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

import static com.project.CinemaTickets.backend.Utils.HelperUtils.createUniqueID;

public class ConverterToImpl implements ConverterTo {
    private Logger logger = LoggerFactory.getLogger(ConverterToImpl.class);

    @Override
    public List<CinemaMovieSession> getCinemaMovieSessionListCinemasList(List<Cinema> cinemaList) {
        logger.info("Start method getCinemaMovieSessionListCinemasList() at " + LocalDateTime.now());
        long startTime = System.currentTimeMillis();
        List<CinemaMovieSession> cinemaMovieSessionList = new LinkedList<>();
        Map<String, Movie> movieNameMap = new HashMap<>();

        cinemaList.forEach( cinema -> {
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
}
