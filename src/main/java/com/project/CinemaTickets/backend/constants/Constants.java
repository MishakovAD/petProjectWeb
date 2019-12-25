package com.project.CinemaTickets.backend.constants;

import java.util.regex.Pattern;

public interface Constants {
    //------------------------------Constants----------------------------------//
    //------------------------------PlHttpClient----------------------------------//
    //---------------------HEADERS---------------------
    public static String authority = "www.kinopoisk.ru";
    public static String method = "GET";
    public static String scheme = "https";
    public static String accept_encoding = "gzip, deflate, br";
    public static String accept_language = "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7";
    public static String sec_fetch_mode = "navigate";
    public static String sec_fetch_cite = "none";
    public static String sec_fetch_user = "?1";
    public static String upgrade_insecure_requests = "1";
    public static String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
    public static String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/536.36";
    //---------------------HEADERS---------------------
    //TODO: Текст изменился, нужно актуализировать!
    public static String CHECK_ANTI_SPAM = "Если вы&nbsp;видите эту страницу, значит с&nbsp;вашего IP-адреса поступило необычно много запросов.";
    //-------------------------------------------------------------------------//

    //------------------------------PlParserAfisha------------------------------------//
    public static String HELPER_FOR_QUERY_AFISHA = "afisha.ru ";
    public String CITY_MOSCOW = "Москва";
    //--------------------------------------------------------------------------------//


    //------------------------------PlParserKinopoisk------------------------------------//
    public static String HELPER_FOR_QUERY_KINOPOISK = "купить билеты kinopoisk.ru afisha";
    public static String HELPER_FOR_BUY_TICKETS = "https://tickets.widget.kinopoisk.ru/w/sessions/";
    public static String[] TYPES_OF_SHOW_FILM = {"2D", "3D", "IMax", "Dolby Atmos"};
    //-----------------------------------------------------------------------------------//


    //------------------------------PATTERNS------------------------------------//
    public static Pattern PATTERN_URL_BUY_TICKETS = Pattern.compile("https://tickets.widget.kinopoisk.ru/w/sessions/");
    public static Pattern PATTERN_TIME = Pattern.compile("(\\d+):(\\d+)");
    public static Pattern PATTERN_CINEMA_KINOPOISK = Pattern.compile("(https://www.kinopoisk.ru/afisha/city/\\d+/cinema/[a-z0-9A-Zа-яА-Я -]+/?)");
    public static Pattern PATTERN_CINEMA_AFISHA_FIRST = Pattern.compile("(https://www.afisha.ru/movie/[0-9]+/?)");
    public static Pattern PATTERN_CINEMA_AFISHA_SECOND = Pattern.compile("(https://www.afisha.ru/\\w+/schedule_cinema_product/[0-9]+/?)");
    public static String HELPER_FOR_QUERY_YANDEX_AFISHA = "купить билеты afisha.yandex.ru ";
    public static Pattern PATTERN_CINEMA_YANDEX_AFISHA = Pattern.compile("(https://afisha.yandex.ru/moscow/cinema/places/[a-z0-9A-Zа-яА-Я -]+)"); //возможно в конце нужен символ /

    //-------------------------------------------------------------------------//




    //-------------------------------------------------------------------------//

    /**
     * Метод, инициализирующий статические переменные из настроек.
     */
    void initPropertiesConst();
}
