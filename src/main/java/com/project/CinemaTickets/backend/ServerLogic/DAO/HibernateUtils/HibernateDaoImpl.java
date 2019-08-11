package com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import com.project.CinemaTickets.backend.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HibernateDaoImpl implements HibernateDao {
    private Logger logger = LoggerFactory.getLogger(HibernateDaoImpl.class);

    @Override
    public boolean saveCinemaMovieSessionObj(List<CinemaMovieSession> cinemaMovieSessionList) {
        Set<Cinema> uniqueCinemaSet = new HashSet<>();
        Set<Movie> uniqueMovieSet = new HashSet<>();
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            cinemaMovieSessionList.forEach(cinemaMovieSession -> {
                Cinema cinema = cinemaMovieSession.getCinema(); //просто так save сштуьф не прокатит. и кинотеатр и фильм не должны повторяться.
                session.save(cinema);
                tx1.commit();
                session.close();
            });
            return true;
        } catch (Exception ex) {
            logger.error("#### ERROR #### at HibernateDaoImpl.saveCinemaMovieSessionObj", ex);
            return false;
        }
    }
}
