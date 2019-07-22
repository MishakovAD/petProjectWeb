package com.project.CinemaTickets.backend.ServerLogic.DAO;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAOServerLogicImpl {
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

        DAOServerLogicImpl dao = new DAOServerLogicImpl();
        dao.save(cinema);
    }
}
