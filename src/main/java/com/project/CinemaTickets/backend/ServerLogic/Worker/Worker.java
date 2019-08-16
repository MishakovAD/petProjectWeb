package com.project.CinemaTickets.backend.ServerLogic.Worker;

import java.net.URLConnection;

/**
 * Класс, предназначенный для заполнения Базы Данных из интернета
 * по определенным условиям, а так же по расписанию.
 */
public interface Worker {
    public String start(StringBuilder capthaDocument);
}
