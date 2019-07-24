package com.project.CinemaTickets.backend.Utils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;

public class Convertor {
    public static Cinema convertOldCinemaToNewCinema (com.project.CinemaTickets.CinemaEntity.Cinema cinema) {
        Cinema newCinema = new Cinema();
        newCinema.setCinemaName(cinema.getName());

        return newCinema;
    }
}
