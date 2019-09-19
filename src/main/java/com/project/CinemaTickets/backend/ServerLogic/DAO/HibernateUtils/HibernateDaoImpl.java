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
    private Map<String, Cinema> uniqueCinemasMap = new HashMap<>();
    private Map<String, Movie> uniqueMoviesMap = new HashMap<>();
    private Set<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> uniqueSessionsSet = new HashSet<>();
    private Set<Cinema_Movie> uniqueCinema_MovieSet = new HashSet<>();

    private Session session;
    private Transaction tx1;

    @PostConstruct
    public void init() {
        logger.info("init() in HibernateDaoImpl.class");
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
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
        tx1 = session.beginTransaction();
        //init(); //Для работающего приложения - не нужно
        try {
            Set<String> cinemasNameSet = new HashSet<>(uniqueCinemasMap.keySet());
            Set<String> moviesNameSet = new HashSet<>(uniqueMoviesMap.keySet());
            cinemaMovieSessionList.forEach(cinemaMovieSession -> {
                Cinema_Movie cinema_movie = new Cinema_Movie();
                com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session sessionObj = cinemaMovieSession.getSession();
                Cinema cinemaObj = cinemaMovieSession.getCinema();
                Movie movieObj = cinemaMovieSession.getMovie();

                if (cinemaObj != null) {
                    if(!cinemasNameSet.contains(cinemaObj.getCinemaName())) {
                        uniqueCinemasMap.put(cinemaObj.getCinemaName(), cinemaObj);
                        cinemasNameSet.add(cinemaObj.getCinemaName());
                        cinema_movie.setCinemaId(cinemaObj.getCinema_id());
                        session.save(cinemaObj);
                        logger.info("cinema save at " + LocalDateTime.now());
                    } else if (sessionObj != null) {
                        String cinemaId = uniqueCinemasMap.get(cinemaObj.getCinemaName()).getCinema_id();
                        cinema_movie.setCinemaId(cinemaId);
                        sessionObj.setCinema_id(cinemaId);
                    }
                }

                if (movieObj != null) {
                    if(!moviesNameSet.contains(movieObj.getMovieName())) {
                        uniqueMoviesMap.put(movieObj.getMovieName(), movieObj);
                        moviesNameSet.add(movieObj.getMovieName());
                        cinema_movie.setMovieId(movieObj.getMovie_id());
                        session.save(movieObj);
                        logger.info("movie save at " + LocalDateTime.now());
                    } else if (sessionObj != null) {
                        String movieId = uniqueMoviesMap.get(movieObj.getMovieName()).getMovie_id();
                        cinema_movie.setMovieId(movieId);
                        sessionObj.setMovie_id(movieId);
                    }
                }

                if (cinemaObj != null && movieObj != null) {
                    if (!uniqueCinema_MovieSet.contains(cinema_movie)) {
                        session.save(cinema_movie);
                        uniqueCinema_MovieSet.add(cinema_movie);
                        logger.info("cinema_movie save at " + LocalDateTime.now());
                    }
                }

                if (sessionObj != null) {
                    List sList = uniqueSessionsSet.stream().filter(s -> s.getUrl().equals(sessionObj.getUrl())).collect(Collectors.toList());
                    if(!uniqueSessionsSet.contains(sessionObj) && sList.size() == 0) {
                        uniqueSessionsSet.add(sessionObj);
                        session.save(sessionObj);
                        logger.info("session save at " + LocalDateTime.now());
                    }
                }
            });
            return true;
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.saveCinemaMovieSessionObj()", ex);
            return false;
        } finally {
            commitChanges();
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

    @Override
    public void commitChanges() {
        tx1.commit();
    }
}
