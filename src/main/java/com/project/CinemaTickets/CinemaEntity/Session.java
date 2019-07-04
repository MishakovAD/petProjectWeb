package com.project.CinemaTickets.CinemaEntity;

public class Session {
    private String timeOfShow; //Time of Movie
    private String typeOfMovie; //2D,3D,IMax
    private String price;

    public Session() {
    }

    public Session(String timeOfShow, String typeOfMovie, String price) {
        this.timeOfShow = timeOfShow;
        this.typeOfMovie = typeOfMovie;
        this.price = price;
    }

    public String getTimeOfShow() {
        return timeOfShow;
    }

    public void setTimeOfShow(String timeOfShow) {
        this.timeOfShow = timeOfShow;
    }

    public String getTypeOfMovie() {
        return typeOfMovie;
    }

    public void setTypeOfMovie(String typeOfMovie) {
        this.typeOfMovie = typeOfMovie;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Session{" +
                "timeOfShow='" + timeOfShow + '\'' +
                ", typeOfMovie='" + typeOfMovie + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
