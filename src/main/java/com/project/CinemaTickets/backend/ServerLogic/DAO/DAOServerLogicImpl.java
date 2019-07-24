package com.project.CinemaTickets.backend.ServerLogic.DAO;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOServerLogicImpl implements DAOServerLogic {
    private Logger logger = LoggerFactory.getLogger(DAOServerLogicImpl.class);

    private final String url = "jdbc:postgresql://localhost/petprojectweb";
    private final String user = "postgres";
    private final String password = "postgres";



    @Override
    public boolean insertCinemaToDB(Cinema cinema) {
        logger.info("Start insert Cinema to database in DAOServerLogicImpl.class");
        StringBuilder INSERT_CINEMA_SQL = new StringBuilder();
        String cinemaName = cinema.getCinemaName();
        String cinemaAddress = cinema.getCinemaAddress();
        String cinemaUnderground = cinema.getCinemaUnderground();
        String urlToAfisha = cinema.getUrlToAfisha();
        String urlToYandexAfisha = cinema.getUrlToYandexAfisha();
        String urlToKinopoisk = cinema.getUrlToKinopoisk();
        String infoAboutCinema = cinema.getInfoAboutCinema();
        List<Movie> movieList = cinema.getMovieList();

        if (cinemaName != null && !cinemaName.isEmpty() && !cinemaName.equals("")) {
            INSERT_CINEMA_SQL.append("INSERT INTO cinema (cinema_name, cinema_address, " +
                    "cinema_underground, url_to_afisha, url_to_yandex_afisha, url_to_kinopoisk, " +
                    "info_about_cinema) VALUES ('" + cinemaName + "', '"
                    + cinemaAddress + "', '" + cinemaUnderground + "', '" + urlToAfisha
                    + "', '" + urlToYandexAfisha + "', '" + urlToKinopoisk + "', '" + infoAboutCinema + "');");
        }

        execureQuery(INSERT_CINEMA_SQL.toString());

        if (movieList != null && movieList.size() > 0) {
            movieList.stream().forEach( movie -> insertMovieToDB(cinema, movie) );
        }
        logger.info("End of insert Cinema to database in DAOServerLogicImpl.class");
        return false;
    }

    @Override
    public boolean insertMovieToDB(Cinema cinema, Movie movie) {
        logger.info("Start insert Movie to database in DAOServerLogicImpl.class");
        String parent = cinema.getCinemaName();
        StringBuilder INSERT_MOVIE_SQL = new StringBuilder();
        String movieName = movie.getMovieName();
        String movieRating = movie.getMovieRating();
        String movieDate = movie.getMovieDate();
        List<Session> sessionList = movie.getSessionList();

        if (movieName != null && !movieName.isEmpty() && !movieName.equals("")) {
            INSERT_MOVIE_SQL.append("INSERT INTO movie (movie_name, movie_rating, " +
                    "movie_date, parent) VALUES ('" + movieName + "', '"
                    + movieRating + "', '" + movieDate + "', '" + parent + "' );");
        }

        if (sessionList != null && sessionList.size() > 0) {
            execureQuery(INSERT_MOVIE_SQL.toString());
            sessionList.stream().forEach( session -> insertSessionToDB(cinema, movie, session) );
        }

        logger.info("End of insert Movie to database in DAOServerLogicImpl.class");
        return false;
    }

    @Override
    public boolean insertSessionToDB(Cinema cinema, Movie movie, Session session) {
        logger.info("Start insert Session to database in DAOServerLogicImpl.class");
        String cinemaName = cinema.getCinemaName();
        String movieName = movie.getMovieName();
        StringBuilder parentBuilder = new StringBuilder();
        parentBuilder.append(cinemaName).append(".").append(movieName);
        String parent = parentBuilder.toString();
        StringBuilder INSERT_SESSION_SQL = new StringBuilder();
        String timeOfShow = session.getTimeOfShow(); //Time of Movie
        String typeOfMovie = session.getTypeOfMovie(); //2D,3D,IMax
        String price = session.getPrice();
        String url = session.getUrl();
        String sessionDate = session.getSessionDate();

        if (timeOfShow != null && !timeOfShow.isEmpty() && !timeOfShow.equals("")) {
            INSERT_SESSION_SQL.append("INSERT INTO session (time_of_show, type_of_show, " +
                    "price, url_for_buy_tickets, session_date, parent) VALUES ('" + timeOfShow + "', '"
                    + typeOfMovie + "', '" + price + "', '"
                    + url + "', '" + sessionDate + "', '" + parent + "' );");
            execureQuery(INSERT_SESSION_SQL.toString());
        }

        logger.info("End of insert Session to database in DAOServerLogicImpl.class");
        return false;
    }

    @Override
    public boolean updateCinema(Cinema cinema) {
        return false;
    }

    @Override
    public boolean updateMovie(Movie movie) {
        return false;
    }

    @Override
    public boolean updateSession(Session session) {
        return false;
    }

    @Override
    public boolean removeCinema(Cinema cinema) {
        return false;
    }

    @Override
    public boolean removeMovie(Movie movie) {
        return false;
    }

    @Override
    public boolean removeSession(Session session) {
        return false;
    }

    @Override
    public boolean selectCinema(String cinemaName) {
        return false;
    }

    @Override
    public boolean selectCinema(int cinemaId) {
        return false;
    }

    @Override
    public boolean selectMovie(String movieName) {
        return false;
    }

    @Override
    public boolean selectMovie(int movieId) {
        return false;
    }

    @Override
    public boolean selectSession(Cinema cinema) {
        return false;
    }

    @Override
    public boolean selectSession(Movie movie) {
        return false;
    }

    private Connection connect() {
        logger.debug("Start create connection in DAOServerLogicImpl.class");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            logger.debug("Connected to the PostgreSQL server successfully in DAOServerLogicImpl.class");

        } catch (SQLException e) {
            logger.error("Error in DAOServerLogicImpl.class", e);
        }

        return conn;
    }

    private void execureQuery (String query) {
        Statement stmnt = null;
        try {
            stmnt = connect().createStatement();
            stmnt.execute(query);
            stmnt.close();
        } catch (SQLException e) {
            logger.error("Error in DAOServerLogicImpl.class in exequte query", e);
        }
    }

    public static void main(String[] args) throws SQLException {
        Cinema cinema = new Cinema();
        cinema.setCinemaName("Test");
        cinema.setCinemaAddress("Test");
        cinema.setCinemaUnderground("Test");
        cinema.setInfoAboutCinema("Test");
        cinema.setUrlToAfisha("Test");
        cinema.setUrlToKinopoisk("Test");
        cinema.setUrlToYandexAfisha("Test");

        Movie movie = new Movie();
        movie.setMovieName("test");
        movie.setMovieDate("test");
        movie.setMovieRating("test");

        Session session = new Session();
        session.setPrice("test");
        session.setSessionDate("test");
        session.setTimeOfShow("test");
        session.setTypeOfMovie("test");
        session.setUrl("test");

        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        movie.setSessionList(sessionList);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        cinema.setMovieList(movieList);



        DAOServerLogicImpl dao = new DAOServerLogicImpl();
        Statement s = dao.connect().createStatement();
        //s.execute("SELECT cinema_id FROM movie");
        dao.insertCinemaToDB(cinema);
    }
}
