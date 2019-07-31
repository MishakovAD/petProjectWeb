package com.project.CinemaTickets.backend.ServerLogic.UpdaterResults;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;

import java.util.List;
import java.util.Map;

/**
 * Класс, предназначенный для подбора самого лучшего предложения кино
 */
public interface UpdaterResult {
    List<Session> updateFromPrice(List<Session> sessionList, String price);
    List<Session> updateFromType(List<Session> sessionList, String type);
    List<Session> updateFromTime(List<Session> sessionList, String time);
    Map<Session, Cinema> updateFromPlace(List<Session> sessionList, String place);
    Map<Session, Cinema> updateFromPlace(List<Session> sessionList, double userLatitude, double userLongitude);
}
