package com.project.CinemaTickets.backend.ServerLogic.UpdaterResults;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class UpdaterResultImpl implements UpdaterResult {
    private Logger logger = LoggerFactory.getLogger(UpdaterResultImpl.class);

    @Override
    public List<Session> updateFromPrice(List<Session> sessionList, String price) {
        logger.debug("Start updateFromPrice() in UpdaterResultImpl.class");
        int priceInt = Integer.parseInt(price.replaceAll("\\D", ""));
        List<Session> resultList = sessionList.stream().filter( session -> Integer.parseInt(session.getPrice().replaceAll("\\D", "")) <= priceInt).collect(Collectors.toList());
        logger.debug("End of updateFromPrice() in UpdaterResultImpl.class");
        return resultList;
    }

    @Override
    public List<Session> updateFromType(List<Session> sessionList, String type) {
        logger.debug("Start updateFromType() in UpdaterResultImpl.class");

        logger.debug("End of updateFromType() in UpdaterResultImpl.class");
        return null;
    }

    @Override
    public List<Session> updateFromTime(List<Session> sessionList, String time) {
        logger.debug("Start updateFromTime() in UpdaterResultImpl.class");

        logger.debug("End of updateFromTime() in UpdaterResultImpl.class");
        return null;
    }

    @Override
    public List<Session> updateFromPlace(List<Session> sessionList, String place) {
        logger.debug("Start updateFromPlace() in UpdaterResultImpl.class");

        logger.debug("End of updateFromPlace() in UpdaterResultImpl.class");
        return null;
    }

    @Override
    public List<Session> updateFromPlace(List<Session> sessionList, double userLatitude, double userLongitude) {
        logger.debug("Start updateFromPlace() in UpdaterResultImpl.class");

        logger.debug("End of updateFromPlace() in UpdaterResultImpl.class");
        return null;
    }

    public static void main(String[] args) {
        System.out.println("jn 270h".replaceAll("\\D", ""));
    }
}
