package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.backend.ServerLogic.DAO.DAOServerLogic;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlUserLogicFromDB implements PliUserLogicFromDB {
    @Override
    public List<Session> getSessionListForMovie(String movieName, String city) {
        List<Session> sessionListForStart = new ArrayList<>();
        List<Cinema> cinemaListInThisCity = daoServerLogic.selectCinema(city, false, true);
        cinemaListInThisCity.forEach( cinema -> {
            String url = cinema.getUrlToKinopoisk();
            if (url != null && !url.isEmpty()) {
                sessionListForStart.addAll(daoServerLogic.
                        selectSessionListForMovie(url.substring(url.indexOf("cinema/")).replaceAll("\\D", ""), movieName));
            }
        });
        return sessionListForStart;
    }

    @Override
    public List<Cinema> getCinemasListForUserFromDB(String movieName, String time, String type, String place) {
        return null;
    }

    @Override
    public Cinema getCinemaFromDB(int cinemaId, String date) {
        return null;
    }

    @Override
    public Cinema getCinemaFromDB(String cinemaName, String date) {
        return null;
    }



    //-----------------------Injections-----------------------------//
    private DAOServerLogic daoServerLogic;

    @Inject
    public void setDaoServerLogic (DAOServerLogic daoServerLogic) {
        this.daoServerLogic = daoServerLogic;
    }
}
