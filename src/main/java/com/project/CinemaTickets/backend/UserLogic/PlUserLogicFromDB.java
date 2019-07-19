package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;

import java.util.List;

public class PlUserLogicFromDB implements PliUserLogicFromDB {
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
}
