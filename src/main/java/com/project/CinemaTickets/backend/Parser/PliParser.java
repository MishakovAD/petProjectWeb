package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Session;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public interface PliParser {
    /**
     * This method parse HTML page, which we get after query
     * to google and take page with content
     * @param HTMLdoc - HTML page from aфisha.ru
     */
    public void parse(Document HTMLdoc) throws IOException;

    /**
     * Method return counter of page, which return Status 200 OK
     * @param filmId - id of film after query to google
     * @return counter of page with cinema
     * @throws IOException
     */
    public int counterOfPage(String filmId) throws IOException;

    //TODO: try{} catch(Exception e) {change method} must have! for different blocks from searching.
    /**
     * Method create URL to aфisha.ru via query to google
     * Find URL in many search queryes, where we can find film id
     * At feature can help find films in other city
     * because after this method we must get film id via "getFilmIdFromQuery"
     * @param query - simple query ("kupit' biletbI na Aladina")
     * @return URL String with our film in aфisha.ru
     */
    public String createURLFromQueryWithGoogle(String query) throws IOException;
    public String createURLFromQueryWithYandex(String query) throws IOException;
    public String createUrlFromQuery(String queryForUrl) throws IOException;

    public String getFilmIdFromQuery(String query);

    public Cinema getCinemaFromElement(Element element) throws IOException;
    public List<Session> getSessionFromElement(Element element) throws IOException;
}
