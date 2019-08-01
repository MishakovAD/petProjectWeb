package com.project.CinemaTickets.backend.ServerLogic.DAO;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TODO: Добавить првоерку на существующий уже кинотеатр и повторяющиеся не добавлять.
//А лучше брать список кинотеатров из БД. А так же подумать, как можно изменить методы добавления
//Чтобы добавлялись, например, отдельно кинотеатр, отдельно кино, отдельно сессия. Как лучше это сделать?

//TODO: Сделать parent уникальным, так как parent по name может повторятся, необходимо так же добавить в него id элемента или еще что.
// А так же в кинотеатр сущность добавить координаты их и можно использовать в parent. Либо urlToKinopoisk
@Component
public class DAOServerLogicImpl implements DAOServerLogic {
    private Logger logger = LoggerFactory.getLogger(DAOServerLogicImpl.class);
    public static List<Cinema> staticCinemaList = new ArrayList<>();

    private final String url = "jdbc:postgresql://localhost/petprojectweb?currentSchema=petproject";
    private final String user = "home";
    private final String password = "home";

//    private final String url = "jdbc:postgresql://localhost/petprojectweb"; //WORK
//    private final String user = "postgres";
//    private final String password = "postgres";



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
        logger.debug("Start insert Movie to database in DAOServerLogicImpl.class");
        String parent = cinema.getCinemaName();
        String urlFromKinopooisk = cinema.getUrlToKinopoisk();
        if (urlFromKinopooisk != null && !urlFromKinopooisk.isEmpty() && !urlFromKinopooisk.equals("")) {
            parent += "." + urlFromKinopooisk.substring(urlFromKinopooisk.indexOf("cinema/") + 7, urlFromKinopooisk.length() - 1);
        }
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

        logger.debug("End of insert Movie to database in DAOServerLogicImpl.class");
        return false;
    }

    @Override
    public boolean insertSessionToDB(Cinema cinema, Movie movie, Session session) {
        logger.debug("Start insert Session to database in DAOServerLogicImpl.class");
        String cinemaName = cinema.getCinemaName();
        String movieName = movie.getMovieName();
        String urlFromKinopooisk = cinema.getUrlToKinopoisk();

        StringBuilder parentBuilder = new StringBuilder();
        parentBuilder.append(cinemaName).append(".");
        if (urlFromKinopooisk != null && !urlFromKinopooisk.isEmpty() && !urlFromKinopooisk.equals("")) {
            parentBuilder.append(urlFromKinopooisk.substring(urlFromKinopooisk.indexOf("cinema/") + 7, urlFromKinopooisk.length() - 1));
        }
        parentBuilder.append(".").append(movieName);

        String parent = parentBuilder.toString();
        StringBuilder INSERT_SESSION_SQL = new StringBuilder();
        String timeOfShow = session.getTimeOfShow(); //Time of Movie
        String typeOfShow = session.getTypeOfShow(); //2D,3D,IMax
        String price = session.getPrice();
        String url = session.getUrl();
        String sessionDate = session.getSessionDate();

        if (timeOfShow != null && !timeOfShow.isEmpty() && !timeOfShow.equals("")) {
            INSERT_SESSION_SQL.append("INSERT INTO session (time_of_show, type_of_show, " +
                    "price, url_for_buy_tickets, session_date, parent) VALUES ('" + timeOfShow + "', '"
                    + typeOfShow + "', '" + price + "', '"
                    + url + "', '" + sessionDate + "', '" + parent + "' );");
            execureQuery(INSERT_SESSION_SQL.toString());
        }

        logger.debug("End of insert Session to database in DAOServerLogicImpl.class");
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
    public List<Cinema> selectCinema(String cinemaName, boolean selectFromName, boolean selectFromCity) {
        //TODO: Т.к. кинотеатров с одинаковым именем много, то будем возвращать список, а уже потом по месту находить нужные.
        logger.debug("Start selectCinema() in DAOServerLogicImpl.class with name: " + cinemaName);
        String SELECT_CINEMA_SQL = "";
        if (selectFromName) {
            SELECT_CINEMA_SQL = "SELECT * FROM cinema WHERE cinema_name = '" + cinemaName + "';";
        } else if (selectFromCity) {
            SELECT_CINEMA_SQL = "SELECT * FROM cinema WHERE cinema_address LIKE 'г. " + cinemaName + "%';";
        }

        List<Cinema> cinemaList = executeQuerySelectForCinema(SELECT_CINEMA_SQL);

        logger.debug("End of selectCinema() in DAOServerLogicImpl.class");
        return cinemaList;
    }

    @Override
    public Cinema selectCinema(int cinemaIdFromKinopoisk) {
        logger.debug("Start selectCinema() in DAOServerLogicImpl.class with id: " + cinemaIdFromKinopoisk);

        String url = "https://www.kinopoisk.ru/afisha/city/1/cinema/" + String.valueOf(cinemaIdFromKinopoisk) + "/";
        String SELECT_CINEMA_SQL = "SELECT * FROM cinema WHERE url_to_kinopoisk = '" + url + "';";
        List<Cinema> cinemaList = executeQuerySelectForCinema(SELECT_CINEMA_SQL);

        if (cinemaList.size() > 1) {
            cinemaList.forEach( (cinemaElem) -> {
                if (cinemaList.size() == 1) {
                    return;
                }
                removeCinema(cinemaElem);
                cinemaList.remove(cinemaElem);
            });
        }

        logger.debug("End of selectCinema() in DAOServerLogicImpl.class");
        return cinemaList.get(0);
    }

    @Override
    public List<Movie> selectMovie(String movieName, boolean selectFromName, boolean selectFromParent) {
        logger.debug("Start selectCinema() in DAOServerLogicImpl.class");
        StringBuilder SQL_SELECT_FOR_MOVIE = new StringBuilder();
        List<Movie> movieList;

        if (selectFromName && selectFromParent) {
            //так выставлять просто нельзя!
            SQL_SELECT_FOR_MOVIE.append("SELECT * FROM movie WHERE lower(movie_name) = LOWER('" + movieName + "')");
        } else if (selectFromParent) {
            SQL_SELECT_FOR_MOVIE.append("SELECT * FROM movie WHERE parent LIKE '" + movieName + ".%'");
        } else if (selectFromName) {
            SQL_SELECT_FOR_MOVIE.append("SELECT * FROM movie WHERE lower(movie_name) = LOWER('" + movieName + "')");
        }
        movieList = executeQuerySelectForMovie(SQL_SELECT_FOR_MOVIE.toString());

        logger.debug("End of selectCinema() in DAOServerLogicImpl.class");
        return movieList;
    }


    @Override
    public List<Session> selectSession(Cinema cinema) {
        logger.debug("Start selectSession() in DAOServerLogicImpl.class");
        String urlFromKinopooisk = cinema.getUrlToKinopoisk();

        String parentBuilder = "";
        if (urlFromKinopooisk != null && !urlFromKinopooisk.isEmpty() && !urlFromKinopooisk.equals("")) {
            parentBuilder = urlFromKinopooisk.substring(urlFromKinopooisk.indexOf("cinema/") + 7, urlFromKinopooisk.length() - 1);
        }

        String SQL_FOR_SELECT_SESSION = "SELECT * FROM session WHERE parent LIKE '%."+ parentBuilder + ".%'";
        List<Session> sessionList = executeQuerySelectForSession(SQL_FOR_SELECT_SESSION);
        logger.debug("End of selectSession() in DAOServerLogicImpl.class");
        return sessionList;
    }

    @Override
    public List<Session> selectSession(Movie movie) {
        logger.debug("Start selectSession() in DAOServerLogicImpl.class");
        String SQL_FOR_SELECT_SESSION = "SELECT * FROM session WHERE parent LIKE '%." + movie.getMovieName() + "'";
        List<Session> sessionList = executeQuerySelectForSession(SQL_FOR_SELECT_SESSION);
        logger.debug("End of selectSession() in DAOServerLogicImpl.class");
        return sessionList;
    }

    @Override
    public List<Session> selectSessionListForMovie(String cinemaKinopoiskId, String movieName) {
        String SQL_FOR_SELECT_SESSION_FOR_MOVIE = "SELECT * FROM session WHERE session.parent LIKE '%."
                + cinemaKinopoiskId + "." + movieName + "'";
        List<Session> sessionList = executeQuerySelectForSession(SQL_FOR_SELECT_SESSION_FOR_MOVIE);
        return sessionList;
    }

    @Override
    public List<Session> selectSession(Movie movie, boolean forTypeAndPrice) {
        logger.debug("Start selectSession() in DAOServerLogicImpl.class");
        if (forTypeAndPrice) {
            String SQL_FOR_SELECT_SESSION = "SELECT * FROM session WHERE " +
                    "parent LIKE '" + movie.getParent() + "." + movie.getMovieName() + "'";
            List<Session> sessionList = executeQuerySelectForSession(SQL_FOR_SELECT_SESSION);
            logger.debug("End of selectSession() in DAOServerLogicImpl.class");
            return sessionList;
        } else {
            return selectSession(movie);
        }

    }

    @Override
    public Map<Session, Cinema> getCinemaWithSessions(List<Session> sessionList) {
        if (staticCinemaList.size() == 0) {
            staticCinemaList = selectAllCinema();
        }
        Map<Session, Cinema> sessionCinemaMap = new HashMap<>();
        sessionList.forEach( session -> {
            List<Cinema> cinemaForMap = staticCinemaList.stream().
                    filter(cinema -> StringUtils.contains(
                            session.getParent(), cinema.getUrlToKinopoisk().
                            substring(cinema.getUrlToKinopoisk().indexOf("cinema/")).
                            replaceAll("\\D", ""))
                          ).collect(Collectors.toList());
            if (cinemaForMap != null && cinemaForMap.size() > 0) {
                sessionCinemaMap.put(session, cinemaForMap.get(0));
            }
        });
        return sessionCinemaMap;
    }

    @Override
    public List<Cinema> selectAllCinema() {
        logger.debug("Start selectAllCinema() in DAOServerLogicImpl.class");

        String SELECT_ALL_CINEMA_SQL = "SELECT * FROM cinema;";
        List<Cinema> allCinemaList = executeQuerySelectForCinema(SELECT_ALL_CINEMA_SQL);

        logger.debug("End of selectAllCinema() in DAOServerLogicImpl.class");
        return allCinemaList;
    }

    @Override
    public List<Movie> selectAllMovie() {
        logger.debug("Start selectAllMovie() in DAOServerLogicImpl.class");

        String SELECT_ALL_MOVIE_SQL = "SELECT DISTINCT ON (movie_name) movie_name, movie_rating, movie_date, parent FROM movie;";
        List<Movie> allMovieList = executeQuerySelectForMovie(SELECT_ALL_MOVIE_SQL);

        logger.debug("End of selectAllMovie() in DAOServerLogicImpl.class");
        return allMovieList;
    }

    @Override
    public List<Session> selectAllSession() {
        logger.debug("Start selectAllSession() in DAOServerLogicImpl.class");

        String SELECT_ALL_SESSION_SQL = "SELECT * FROM session;";
        List<Session> allSessionList = executeQuerySelectForSession(SELECT_ALL_SESSION_SQL);

        logger.debug("End of selectAllSession() in DAOServerLogicImpl.class");
        return allSessionList;
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

    private List<Cinema> executeQuerySelectForCinema(String queryCinema) {
        Statement stmnt = null;
        List<Cinema> cinemaList = new ArrayList<>();
        Cinema cinema;
        try {
            stmnt = connect().createStatement();
            ResultSet resultSet = stmnt.executeQuery(queryCinema);
            while (resultSet.next()) {
                cinema = new Cinema();
                cinema.setCinema_id(Integer.parseInt(resultSet.getString("cinema_id")));
                cinema.setCinemaName(resultSet.getString("cinema_name"));
                cinema.setCinemaAddress(resultSet.getString("cinema_address"));
                cinema.setCinemaUnderground(resultSet.getString("cinema_underground"));
                cinema.setUrlToKinopoisk(resultSet.getString("url_to_kinopoisk"));
                cinemaList.add(cinema);
            }
            stmnt.close();
        } catch (SQLException e) {
            logger.error("Error in DAOServerLogicImpl.class in exequte query", e);
        }
        return cinemaList;
    }

    private List<Movie> executeQuerySelectForMovie(String queryMovie) {
        Statement stmnt = null;
        List<Movie> movieList = new ArrayList<>();
        Movie movie;
        try {
            stmnt = connect().createStatement();
            ResultSet resultSet = stmnt.executeQuery(queryMovie);
            while (resultSet.next()) {
                movie = new Movie();
                movie.setMovieName(resultSet.getString("movie_name"));
                movie.setMovieRating(resultSet.getString("movie_rating"));
                movie.setParent(resultSet.getString("parent"));
                movieList.add(movie);
            }
            stmnt.close();
        } catch (SQLException e) {
            logger.error("Error in DAOServerLogicImpl.class in exequte query", e);
        }
        return movieList;
    }

    private List<Session> executeQuerySelectForSession(String querySession) {
        Statement stmnt = null;
        List<Session> sessionList = new ArrayList<>();
        Session session;
        try {
            stmnt = connect().createStatement();
            ResultSet resultSet = stmnt.executeQuery(querySession);
            while (resultSet.next()) {
                session = new Session();
                session.setTimeOfShow(resultSet.getString("time_of_show"));
                session.setTypeOfShow(resultSet.getString("type_of_show"));
                session.setPrice(resultSet.getString("price"));
                session.setSessionDate(resultSet.getString("session_date"));
                session.setUrl(resultSet.getString("url_for_buy_tickets"));
                session.setParent(resultSet.getString("parent"));
                sessionList.add(session);
            }
            stmnt.close();
        } catch (SQLException e) {
            logger.error("Error in DAOServerLogicImpl.class in exequte query", e);
        }
        return sessionList;
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
        movie.setMovieName("Аладдин");
        movie.setMovieDate("test");
        movie.setMovieRating("test");
        movie.setParent("Формула Кино Жемчужина");

        Session session = new Session();
        session.setPrice("test");
        session.setSessionDate("test");
        session.setTimeOfShow("test");
        session.setTypeOfShow("test");
        session.setUrl("test");

        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        movie.setSessionList(sessionList);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        cinema.setMovieList(movieList);



        DAOServerLogicImpl dao = new DAOServerLogicImpl();
        Statement s = dao.connect().createStatement();
        List<Session> sList = dao.selectSessionListForMovie("281234", "Король Лев");
        List<Cinema> cinemas = dao.selectCinema("Москва", false, true);
//        List<Movie> movies = dao.selectAllMovie();
//        List<Session> sessions = dao.selectAllSession();
//        Map<Session, Cinema> map = dao.getCinemaWithSessions(sessions);
//        List<Session> sessionsMovie = dao.selectSession(movie);
        //s.execute("SELECT cinema_id FROM movie");
        //dao.insertCinemaToDB(cinema);
    }
}
