package com.project.CinemaTickets.CinemaEntity;

public class Session {
    private int sessionId;
    private String timeOfShow; //Time of Movie
    private String typeOfMovie; //2D,3D,IMax
    private String price;
    private String url;
    private String movieDate;

    public Session() {
    }

    public Session(String timeOfShow, String typeOfMovie, String price) {
        this.timeOfShow = timeOfShow;
        this.typeOfMovie = typeOfMovie;
        this.price = price;
    }

    public Session(String timeOfShow, String typeOfMovie, String price, String url) {
        this.timeOfShow = timeOfShow;
        this.typeOfMovie = typeOfMovie;
        this.price = price;
        this.url = url;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    @Override
    public String toString() {
        return "Расписание{" +
                "Время='" + timeOfShow + '\'' +
                ", Тип='" + typeOfMovie + '\'' +
                ", Цена='" + price + '\'' +
                ", Дата='" + movieDate + '\'' +
                '}';
    }
}
