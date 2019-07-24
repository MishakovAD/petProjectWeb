package com.project.CinemaTickets.backend.ServerLogic.DAO.EntityDAO;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CinemaDAOImpl implements CinemaDAO {
    public void save(Cinema cinema) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(cinema);
        tx1.commit();
        session.close();
    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.setCinemaName("Test");
        cinema.setCinemaAddress("Test");
        cinema.setCinemaUnderground("Test");
        cinema.setInfoAboutCinema("Test");

        CinemaDAOImpl dao = new CinemaDAOImpl();
        dao.save(cinema);
    }
}
