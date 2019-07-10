package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;

import java.io.IOException;

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
    public String getUrlForBuyTickets(Cinema cinema, Movie movie) throws IOException;

    public String createURLFromQueryWithGoogle(String url) throws IOException;
    public String createURLFromQueryWithYandex(String url) throws IOException;
    public String createUrlFromQuery(String queryForUrl) throws IOException;
}
