package com.project.CinemaTickets.CinemaEntity;

public class Cinema {
    private String name;
    private String address;
    private String infoAboutCinema;

    public Cinema() {
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
}
