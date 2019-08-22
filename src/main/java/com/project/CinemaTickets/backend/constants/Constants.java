package com.project.CinemaTickets.backend.constants;

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
    public static String CHECK_ANTI_SPAM = "Если вы&nbsp;видите эту страницу, значит с&nbsp;вашего IP-адреса поступило необычно много запросов.";
    //-------------------------------------------------------------------------//



    //-------------------------------------------------------------------------//
    //-------------------------------------------------------------------------//




    //-------------------------------------------------------------------------//

    /**
     * Метод, инициализирующий статические переменные из настроек.
     */
    void initPropertiesConst();
}
