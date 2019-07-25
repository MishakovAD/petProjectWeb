package com.project.CinemaTickets.CinemaEntity;

import java.util.List;

//TODO: сделать еще два поля: долгота и широта и заполнить их по адресу
public class Cinema {
    private int cinemaId;
    private String name;
    private String address;
    private String underground;
    private String urlToAfisha;
    private String urlToYandexAfisha;
    private String urlToKinopoisk;
    private String infoAboutCinema;
    private List<Movie> movieList; //по сути это лист с одинаковыми фильмами, но разным расписанием. Поэтому в метод кинопоиска передаем и кинотеатр и нужный фильм - т.е. нужный сеанс.

    public Cinema() {
    }

    public Cinema(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnderground() {
        return underground;
    }

    public void setUnderground(String underground) {
        this.underground = underground;
    }

    public String getInfoAboutCinema() {
        return infoAboutCinema;
    }

    public void setInfoAboutCinema(String infoAboutCinema) {
        this.infoAboutCinema = infoAboutCinema;
    }

    public String getUrlToAfisha() {
        return urlToAfisha;
    }

    public void setUrlToAfisha(String urlToAfisha) {
        this.urlToAfisha = urlToAfisha;
    }

    public String getUrlToYandexAfisha() {
        return urlToYandexAfisha;
    }

    public void setUrlToYandexAfisha(String urlToYandexAfisha) {
        this.urlToYandexAfisha = urlToYandexAfisha;
    }

    public String getUrlToKinopoisk() {
        return urlToKinopoisk;
    }

    public void setUrlToKinopoisk(String urlToKinopoisk) {
        this.urlToKinopoisk = urlToKinopoisk;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movie) {
        this.movieList = movie;
    }

    @Override
    public String toString() {
        return "Кинотеатр {" +
//                "cinemaId=" + cinemaId +
                ", Название='" + name + '\'' +
//                ", Адрес='" + address + '\'' +
                ", Метро='" + underground + '\'' +
                ", urlToAfisha='" + urlToAfisha + '\'' +
//                ", urlToYandexAfisha='" + urlToYandexAfisha + '\'' +
//                ", urlToKinopoisk='" + urlToKinopoisk + '\'' +
//                ", Информация='" + infoAboutCinema + '\'' +
                '}';
    }
}
