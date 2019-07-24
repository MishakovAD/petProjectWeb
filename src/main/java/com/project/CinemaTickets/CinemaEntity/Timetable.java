package com.project.CinemaTickets.CinemaEntity;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> timetable;

    public Timetable() {
        timetable = new ArrayList<>();
    }

    public Timetable(List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> timetable) {
        this.timetable = timetable;
    }

    public List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session> timetable) {
        this.timetable = timetable;
    }

    public void setSession (com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session session) {
        timetable.add(session);
    }
}
