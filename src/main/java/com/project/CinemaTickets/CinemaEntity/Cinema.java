package com.project.CinemaTickets.CinemaEntity;

import java.util.List;

public class Cinema {
    private int cinemaId;
    private String name;
    private String address;
    private String urlToAfisha;
    private String urlToYandexAfisha;
    private String urlToKinopoisk;
    private String infoAboutCinema;
    private List<Movie> movieList;

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
        return "Кинотеатр{" +
                "cinemaId=" + cinemaId +
                ", Название='" + name + '\'' +
                ", Адрес='" + address + '\'' +
                ", urlToAfisha='" + urlToAfisha + '\'' +
                ", urlToYandexAfisha='" + urlToYandexAfisha + '\'' +
                ", urlToKinopoisk='" + urlToKinopoisk + '\'' +
                ", infoAboutCinema='" + infoAboutCinema + '\'' +
                '}';
    }
}
