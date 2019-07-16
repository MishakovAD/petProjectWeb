package com.project.CinemaTickets.backend.ProxyServer.Exceptions;

import java.io.IOException;

public class ConnectionRefused  extends IOException {

    public ConnectionRefused(String message) {
        super(message);
    }


    public ConnectionRefused(String message, Throwable e) {
        super(message, e);
    }
}
