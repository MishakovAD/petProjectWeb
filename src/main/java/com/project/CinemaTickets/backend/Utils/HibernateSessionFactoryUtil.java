package com.project.CinemaTickets.backend.Utils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateSessionFactoryUtil {
    private static Logger logger = LoggerFactory.getLogger(HibernateSessionFactoryUtil.class);
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        logger.debug("Start method getSessionFactory() at HibernateSessionFactoryUtil.class");
        if (sessionFactory == null) {
            try {
                //TODO: Сделать поиск по все аннотированным классам и добавление их сюда
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Cinema.class);
                configuration.addAnnotatedClass(Movie.class);
                configuration.addAnnotatedClass(Session.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                logger.error("Error at method getSessionFactory() at HibernateSessionFactoryUtil.class: ", e);
            }
        }
        logger.debug("End of method getSessionFactory() at HibernateSessionFactoryUtil.class");
        return sessionFactory;
    }
}
