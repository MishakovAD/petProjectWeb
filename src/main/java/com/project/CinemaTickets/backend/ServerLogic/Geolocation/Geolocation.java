package com.project.CinemaTickets.backend.ServerLogic.Geolocation;

import java.util.List;
import java.util.Map;

/**
 * Класс, предназначенный для работы с координатами.
 * Определение ближайшего кинотеатра, всех кинотеатров на карте, расстояния и тд.
 */
public interface Geolocation {

    /**
     * http://api.sputnik.ru/maps/geocoder/
     * Возвращает координаты в виде карты, где ключ "долгота" или "широта",
     * а значение - значение, которое берем по ключу.
     * @param address - адрес в привычной форме (город обязателен)
     * @return "долгота" - значение, "широта" - значение
     */
    Map<String, Double> getCoordinatesFromAddress(String address);

    boolean isClosesCoordinates (double latitude, double longitude);
}
