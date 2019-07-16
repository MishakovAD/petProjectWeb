package com.project.CinemaTickets.backend.ProxyServer.Exceptions;

public class DocumentNotFoundWithProxyException extends Exception {
    public DocumentNotFoundWithProxyException(String message) {
    super(message);
  }


  public DocumentNotFoundWithProxyException(String message, Throwable e) {
    super(message, e);
  }

}
