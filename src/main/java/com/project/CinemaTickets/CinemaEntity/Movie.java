package com.project.CinemaTickets.CinemaEntity;

public class Movie {
    private String name;
    private String rating;
    private Cinema cinema;
    private int price;
    private String dateFrom;
    private String[] movieShow; //Time of Movie
    private String typeOfMovie; //2D,3D,IMax

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String[] getMovieShow() {
        return movieShow;
    }

    public void setMovieShow(String[] movieShow) {
        this.movieShow = movieShow;
    }

    public String getTypeOfMovie() {
        return typeOfMovie;
    }

    public void setTypeOfMovie(String typeOfMovie) {
        this.typeOfMovie = typeOfMovie;
    }
}
