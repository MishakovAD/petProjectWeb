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
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "movie_rating")
    private String movieRating;

    @Column(name = "movie_date")
    private String movieDate;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cinema_id")
    @Transient
    private Cinema cinema;

    @Transient
    private String movie_id;

    //    @OneToOne(optional=false, cascade=CascadeType.ALL)
//    @JoinColumn (name="session_id")
    @Transient
    private List<Session> sessionList;

    @Transient
    private Session session;

    @Transient
    private String parent;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    public void addSession (Session session) {
        sessionList.add(session);
    }

    public void removeSession (Session session) {
        sessionList.remove(session);
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
