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

    /**
     * Определяет, достаточно ли близко находится
     *  объект к введенным координатам.
     * @param userLatitude - широта пользователя
     * @param userLongitude - долгота пользователя
     * @param objectLatitude - широта объекта (кинотеатра)
     * @param objectLongitude - долгота объекта (кинотеатра)
     * @return true, если достаточно близко
     */
    boolean isClosesCoordinates (double userLatitude, double userLongitude, double objectLatitude, double objectLongitude);

    /**
     * Определяет город по долготе и широте.
     * @param latitude
     * @param longitude
     * @return
     */
    String detectCity (double latitude, double longitude);
//TODO: можно сделат ьпроверку результатов по ip и долготе и широте и если не совпадает спрашивать у пользователя, в том ли он городе.
    /**
     * Определяет город по ip адресу
     * @param ip - ip адрес
     * @return город пользователя
     */
    String detectCity (String ip);
}
