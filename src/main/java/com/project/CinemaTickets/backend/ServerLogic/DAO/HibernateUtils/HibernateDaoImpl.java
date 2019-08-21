package com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema_Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import com.project.CinemaTickets.backend.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Component
public class HibernateDaoImpl implements HibernateDao {
    private Logger logger = LoggerFactory.getLogger(HibernateDaoImpl.class);
    public static Map<String, Cinema> uniqueCinemasMap = new HashMap<>();
    public static Map<String, Movie> uniqueMoviesMap = new HashMap<>();
    public static Set<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> uniqueSessionsSet = new HashSet<>();
    public static Set<Cinema_Movie> uniqueCinema_MovieSet = new HashSet<>();

    private Session session;
    private Transaction tx1;

    @PostConstruct
    public void init() {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        tx1 = session.beginTransaction();
        if (uniqueCinemasMap.size() == 0) {
            selectAllCinema().forEach(cinema -> uniqueCinemasMap.put(cinema.getCinemaName(), cinema));
        }

        if (uniqueMoviesMap.size() == 0) {
            selectAllMovie().forEach(movie -> uniqueMoviesMap.put(movie.getMovieName(), movie));
        }

        if (uniqueSessionsSet.size() == 0) {
            selectAllSession().forEach(session -> uniqueSessionsSet.add(session));
        }

        if (uniqueCinema_MovieSet.size() == 0) {
            selectAllCinema_Movie().forEach(cinema_movie -> {
                cinema_movie.setId(0); //чтобы был сет только уникальных значений, не обращая внимания на Id
                uniqueCinema_MovieSet.add(cinema_movie);
            });
        }
    }

    @PreDestroy
    public void destroy() {
        session.close();
    }

    @Override
    public boolean saveCinemaMovieSessionObj(List<CinemaMovieSession> cinemaMovieSessionList) {
        logger.info("Start method saveCinemaMovieSessionObj() at " + LocalDateTime.now());
        //init(); //Для работающего приложения - не нужно
        try {
            Set<String> cinemasNameList = uniqueCinemasMap.keySet();
            Set<String> moviesNameList = uniqueMoviesMap.keySet();
            //TODO: Нужно решить проблему с исользованием одной и тойже сессии.
            //synchronized (this) {
            cinemaMovieSessionList.forEach(cinemaMovieSession -> {
                Cinema_Movie cinema_movie = new Cinema_Movie();
                com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session sessionObj = cinemaMovieSession.getSession();
                Cinema cinemaObj = cinemaMovieSession.getCinema();
                Movie movieObj = cinemaMovieSession.getMovie();

                if (!cinemasNameList.contains(cinemaObj.getCinemaName())) {
                    uniqueCinemasMap.put(cinemaObj.getCinemaName(), cinemaObj);
                    cinema_movie.setCinemaId(cinemaObj.getCinema_id());
                    session.save(cinemaObj);
                } else {
                    String cinemaId = uniqueCinemasMap.get(cinemaObj.getCinemaName()).getCinema_id();
                    cinema_movie.setCinemaId(cinemaId);
                    sessionObj.setCinema_id(cinemaId);
                }

                if (!moviesNameList.contains(movieObj.getMovieName())) {
                    uniqueMoviesMap.put(movieObj.getMovieName(), movieObj);
                    cinema_movie.setMovieId(movieObj.getMovie_id());
                    session.save(movieObj);
                } else {
                    String movieId = uniqueMoviesMap.get(movieObj.getMovieName()).getMovie_id();
                    cinema_movie.setMovieId(movieId);
                    sessionObj.setMovie_id(movieId);
                }

                if (!uniqueCinema_MovieSet.contains(cinema_movie)) {
                    session.save(cinema_movie);
                    uniqueCinema_MovieSet.add(cinema_movie);
                }

                if (!uniqueSessionsSet.contains(sessionObj)) {
                    session.save(sessionObj);
                }
            });
            tx1.commit();
            //}
            return true;
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.saveCinemaMovieSessionObj()", ex);
            return false;
        } finally {
            uniqueMoviesMap = new HashMap<>();
            uniqueCinemasMap = new HashMap<>();
            uniqueSessionsSet = new HashSet<>();
            logger.info("End of method saveCinemaMovieSessionObj() at " + LocalDateTime.now());
        }
    }

    @Override
    public List<Cinema> selectAllCinema() {
        logger.info("Start method selectAllCinema() at " + LocalDateTime.now());
        List<Cinema> allCinemaList = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            String sql = "From " + Cinema.class.getSimpleName();
            allCinemaList = session.createQuery(sql).list();
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.selectAllCinema", ex);
        }
        logger.info("End of method selectAllCinema() at " + LocalDateTime.now());
        return allCinemaList;
    }

    @Override
    public List<Movie> selectAllMovie() {
        logger.info("Start method selectAllMovie() at " + LocalDateTime.now());
        List<Movie> allMovieList = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            String sql = "From " + Movie.class.getSimpleName();
            allMovieList = session.createQuery(sql).list();
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.selectAllMovie()", ex);
        }
        logger.info("End of method selectAllMovie() at " + LocalDateTime.now());
        return allMovieList;
    }

    @Override
    public List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> selectAllSession() {
        logger.info("Start method selectAllSession() at " + LocalDateTime.now());
        List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> allSessionList = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            String sql = "From " + com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session.class.getSimpleName();
            allSessionList = session.createQuery(sql).list();
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.selectAllSession()", ex);
        }
        logger.info("End of method selectAllSession() at " + LocalDateTime.now());
        return allSessionList;
    }

    @Override
    public List<Cinema_Movie> selectAllCinema_Movie() {
        logger.info("Start method selectAllCinema_Movie() at " + LocalDateTime.now());
        List<Cinema_Movie> allCinema_MovieList = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            String sql = "From " + Cinema_Movie.class.getSimpleName();
            allCinema_MovieList = session.createQuery(sql).list();
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.selectAllCinema_Movie()", ex);
        }
        logger.info("End of method selectAllCinema_Movie() at " + LocalDateTime.now());
        return allCinema_MovieList;
    }

    @Override
    public List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> selectSessionsForMovie(Movie movie) {
        logger.info("Start method selectSessionsForMovie() at " + LocalDateTime.now());
        if (movie.getMovieName() == null || movie.getMovieName().isEmpty() || movie.getMovieName().equals("")) {
            return null;
        }
        List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> sessionList = null;
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Query query = session.createQuery("From "
                    + com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session.class.getSimpleName()
                    + " WHERE movie_id = :movieId");
            query.setParameter("movieId", movie.getMovie_id());
            sessionList = query.list();
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.selectAllCinema_Movie()", ex);
        }
        logger.info("End of method selectSessionsForMovie() at " + LocalDateTime.now());
        return sessionList;
    }
}
