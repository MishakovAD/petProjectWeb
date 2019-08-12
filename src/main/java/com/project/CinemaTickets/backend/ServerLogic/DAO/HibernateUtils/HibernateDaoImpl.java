package com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema_Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import com.project.CinemaTickets.backend.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HibernateDaoImpl implements HibernateDao {
    private Logger logger = LoggerFactory.getLogger(HibernateDaoImpl.class);
    public static Set<String> uniqueCinemasNameSet = new HashSet<>();
    public static Set<String> uniqueMoviesNameSet = new HashSet<>();

    private void init() {
        if (uniqueCinemasNameSet.size() == 0) {
            selectAllCinema().forEach(cinema -> uniqueCinemasNameSet.add(cinema.getCinemaName()));
        }

        if (uniqueMoviesNameSet.size() == 0) {
            selectAllMovie().forEach(movie -> uniqueMoviesNameSet.add(movie.getMovieName()));
        }
    }

    @Override
    public boolean saveCinemaMovieSessionObj(List<CinemaMovieSession> cinemaMovieSessionList) {
        logger.info("Start method saveCinemaMovieSessionObj() at " + LocalDateTime.now());
        init();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            cinemaMovieSessionList.forEach(cinemaMovieSession -> {
                Cinema_Movie cinema_movie = new Cinema_Movie();

                Cinema cinemaObj = cinemaMovieSession.getCinema();
                if (!uniqueCinemasNameSet.contains(cinemaObj.getCinemaName())) {
                    uniqueCinemasNameSet.add(cinemaObj.getCinemaName());
                    cinema_movie.setCinemaId(cinemaObj.getCinema_id());
                    session.save(cinemaObj);
                } else {
                    cinema_movie.setCinemaId(cinemaObj.getCinema_id());
                }

                Movie movieObj = cinemaMovieSession.getMovie();
                if (uniqueMoviesNameSet.contains(movieObj.getMovieName())) {
                    uniqueMoviesNameSet.add(movieObj.getMovieName());
                    cinema_movie.setMovieId(movieObj.getMovie_id());
                    session.save(movieObj);
                } else {
                    cinema_movie.setMovieId(movieObj.getMovie_id());
                }

                session.save(cinema_movie);

                com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session sessionObj = cinemaMovieSession.getSession();
                session.save(sessionObj);

                tx1.commit();
                session.close();
            });
            return true;
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.saveCinemaMovieSessionObj", ex);
            return false;
        } finally {
            uniqueMoviesNameSet = new HashSet<>();
            uniqueCinemasNameSet = new HashSet<>();
            logger.info("End of method saveCinemaMovieSessionObj() at " + LocalDateTime.now());
        }
    }

    @Override
    public List<Cinema> selectAllCinema() {
        logger.info("Start method selectAllCinema() at " + LocalDateTime.now());
        List<Cinema> allCinemaList = new ArrayList<>();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            String sql = "From " + Cinema.class.getSimpleName();
            allCinemaList = session.createQuery(sql).list();
            session.close();
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
            Transaction tx1 = session.beginTransaction();
            String sql = "From " + Movie.class.getSimpleName();
            allMovieList = session.createQuery(sql).list();
            session.close();
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.selectAllMovie", ex);
        }
        logger.info("End of method selectAllMovie() at " + LocalDateTime.now());
        return allMovieList;
    }

    public static void main(String[] args) {
        HibernateDaoImpl h = new HibernateDaoImpl();
        List<Movie> allMovieList = h.selectAllMovie();
        System.out.println("");
    }
}
