package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public interface PliParserKinopoisk {
    /**
     * На вход передаются и кинотеатр и фильм, так как в кинотетре может идти фильм
     * в разное время, а у нас имеется только список кинотеатров с movieList,
     * где хранятся фильм + расписание, поэтому мы и передаем movie
     * с точным временем показа.
     * @param cinema
     * @param movie
     * @return
     * @throws IOException
     */
    public String getUrlForBuyTicketsFromInternet(Cinema cinema, Movie movie) throws IOException;

    public Cinema getCinemaFromDocument(Document document);

    public List<Movie> getMovieListFromDocument(Cinema cinema, Document document);
    public Movie getMovieFromElement(Cinema cinema, Element element);

    public List<Session> getSessionListFromElement(Cinema cinema, Movie movie, Element element);
    public Session getSessionFromElement(Element element);


    public String createURLFromQueryWithGoogle(String url) throws IOException;
    public String createURLFromQueryWithYandex(String url) throws IOException;
    public String createUrlFromQuery(String queryForUrl) throws IOException;
}
