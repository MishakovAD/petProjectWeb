package com.project.CinemaTickets.backend.ServerLogic.DAO.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cinema_name")
    private String cinemaName;

    @Column(name = "cinema_address")
    private String cinemaAddress;

    @Column(name = "cinema_underground")
    private String cinemaUnderground;

    @Column(name = "cinema_city") //В будущем можно вынести в отдельную таблицу. Смысл? Будет ли работать быстрее?
    private String cinemaCity;

    @Column(name = "url_to_afisha")
    private String urlToAfisha;

    @Column(name = "url_to_yandex_afisha")
    private String urlToYandexAfisha;

    @Column(name = "url_to_kinopoisk")
    private String urlToKinopoisk;

    @Column(name = "info_about_cinema")
    private String infoAboutCinema;

    @Column(name = "cinema_id")
    private String cinema_id;

    @Transient
    private List<Movie> movieList;

    public Cinema() {
    }

    public int getId() {
        return id;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getCinemaUnderground() {
        return cinemaUnderground;
    }

    public void setCinemaUnderground(String cinemaUnderground) {
        this.cinemaUnderground = cinemaUnderground;
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

    public String getInfoAboutCinema() {
        return infoAboutCinema;
    }

    public void setInfoAboutCinema(String infoAboutCinema) {
        this.infoAboutCinema = infoAboutCinema;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void addMovie (Movie movie) {
        movieList.add(movie);
    }

    public void removeMovie (Movie movie) {
        movieList.remove(movie);
    }

    public String getCinemaCity() {
        return cinemaCity;
    }

    public void setCinemaCity(String cinemaCity) {
        this.cinemaCity = cinemaCity;
    }

    public String getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(String cinema_id) {
        this.cinema_id = cinema_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema)) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(cinemaName, cinema.cinemaName) &&
                Objects.equals(cinemaAddress, cinema.cinemaAddress) &&
                Objects.equals(cinemaUnderground, cinema.cinemaUnderground) &&
                Objects.equals(cinemaCity, cinema.cinemaCity) &&
                Objects.equals(urlToKinopoisk, cinema.urlToKinopoisk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cinemaName, cinemaAddress, cinemaUnderground, cinemaCity, urlToKinopoisk);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", cinemaName='" + cinemaName + '\'' +
                ", cinemaAddress='" + cinemaAddress + '\'' +
                ", cinemaUnderground='" + cinemaUnderground + '\'' +
                ", urlToAfisha='" + urlToAfisha + '\'' +
                ", urlToYandexAfisha='" + urlToYandexAfisha + '\'' +
                ", urlToKinopoisk='" + urlToKinopoisk + '\'' +
                ", infoAboutCinema='" + infoAboutCinema + '\'' +
                ", movieList=" + movieList +
                '}';
    }
}
