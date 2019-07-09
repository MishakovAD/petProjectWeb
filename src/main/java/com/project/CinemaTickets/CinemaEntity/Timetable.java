package com.project.CinemaTickets.CinemaEntity;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Session> timetable;

    public Timetable() {
        timetable = new ArrayList<>();
    }

    public Timetable(List<Session> timetable) {
        this.timetable = timetable;
    }

    public List<Session> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Session> timetable) {
        this.timetable = timetable;
    }

    public void setSession (Session session) {
        timetable.add(session);
    }
}
