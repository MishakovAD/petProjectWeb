package com.project.CinemaTickets.backend.ServerLogic.DAO.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int session_id;

    @Column(name = "time_of_show")
    private String timeOfShow; //Time of Movie

    @Column(name = "type_of_show")
    private String typeOfShow; //2D,3D,IMax

    @Column(name = "price")
    private String price;

    @Column(name = "url_for_buy_tickets")
    private String url;

    @Column(name = "session_date")
    private String sessionDate;

    //    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private Movie movie;

    @Transient
    private String parent;

    public Session() {
    }

    public int getSession_id() {
        return session_id;
    }

    public String getTimeOfShow() {
        return timeOfShow;
    }

    public void setTimeOfShow(String timeOfShow) {
        this.timeOfShow = timeOfShow;
    }

    public String getTypeOfShow() {
        return typeOfShow;
    }

    public void setTypeOfShow(String typeOfShow) {
        this.typeOfShow = typeOfShow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
