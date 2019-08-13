package com.project.CinemaTickets.backend.ServerLogic.DAO.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @Column(name = "movie_id")
    private String movie_id;

    @Column(name = "cinema_id")
    private String cinema_id;

    @Column(name = "session_id")
    private String session_id;

    //    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private Movie movie;

    @Transient
    private String parent;

    public Session() {
    }

    public int getId() {
        return id;
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

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
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
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(url, session.url) &&
                Objects.equals(session_id, session.session_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, session_id);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", timeOfShow='" + timeOfShow + '\'' +
                ", typeOfShow='" + typeOfShow + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", sessionDate='" + sessionDate + '\'' +
                ", movie=" + movie +
                ", parent='" + parent + '\'' +
                '}';
    }
}
