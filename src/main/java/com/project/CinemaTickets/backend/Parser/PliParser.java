package com.project.CinemaTickets.backend.Parser;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface PliParser {
    /**
     * This method parse HTML page, which we get after query
     * to google and take page with content
     * @param HTMLdoc - HTML page from aфisha.ru
     */
    public void parse(Document HTMLdoc);

    /**
     * Method return counter of page, which return Status 200 OK
     * @param filmId - id of film after query to google
     * @return counter of page with cinema
     * @throws IOException
     */
    public int counterOfPage(String filmId) throws IOException;

    /**
     * Method create URL to aфisha.ru via query to google
     * Find URL in many search queryes, where we can find film id
     * At feature can help find films in other city
     * because after this method we must get film id via "getFilmIdFromQuery"
     * @param query - simple query ("kupit' biletbI na Aladina")
     * @return URL String with our film in aфisha.ru
     */
    public String createURLFromQuery (String query) throws IOException;

    public String getFilmIdFromQuery(String query);
}
