package com.project.CinemaTickets.backend.ServerLogic.DAO.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

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

    @Column(name = "cinema_city")
    private String cinemaCity;

    @Column(name = "url_to_afisha")
    private String urlToAfisha;

    @Column(name = "url_to_yandex_afisha")
    private String urlToYandexAfisha;

    @Column(name = "url_to_kinopoisk")
    private String urlToKinopoisk;

    @Column(name = "info_about_cinema")
    private String infoAboutCinema;

    @Transient
    private String cinema_id;

    //    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private List<Movie> movieList;

    public Cinema() {
    }

    public void setId(int id) {
        this.id = id;
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
