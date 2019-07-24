package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.Parser.PlParserAfisha;
import com.project.CinemaTickets.backend.Parser.PliParser;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ProxyServer.PliProxyServer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: Все sout заменить на Logger
@Component
public class PlUserLogicFromInternet implements PliUserLogicFromInternet {
    public static String[] TYPES_OF_SHOW_FILM = {"2D", "3D", "IMax", "Dolby Atmos"};
    public static Pattern PATTERN_CINEMA_KINOPOISK = Pattern.compile("(https://www.kinopoisk.ru/afisha/city/\\d+/cinema/[a-z0-9A-Zа-яА-Я -]+/?)");
    public static Pattern PATTERN_TIME = Pattern.compile("(\\d+):(\\d+)");

    private Logger logger = LoggerFactory.getLogger(PlUserLogicFromInternet.class);

    private int counterTryGetFilmId = 0;

    @Override
    public List<Cinema> getCinemaListWithMovie(String userQuery) throws IOException {
        logger.info("Start method getCinemaListWithMovie() at " + LocalDateTime.now() + " - with userQuery: " + userQuery);
        List<Cinema> cinemaList = new ArrayList<>();
        counterTryGetFilmId++;
        String urlWithFiltFromAfisha = pliParserAfisha.createUrlFromQuery(userQuery);
        String filmIdFromAfisha = pliParserAfisha.getFilmIdFromQuery(urlWithFiltFromAfisha);

        if (filmIdFromAfisha.equals("0")) {
            getCinemaListWithMovie(userQuery);
        } else {
            System.out.println("До получения корректной ссылки parser() вызвался: " + counterTryGetFilmId + " раз");
            counterTryGetFilmId = 0;

            int pageCounter = pliParserAfisha.counterOfPage(filmIdFromAfisha);
            for (int i = 1; i <= pageCounter; i++) {
                StringBuffer urlStr = new StringBuffer();
                //TODO: At this time, this work only for Moscow. At future change "msk" on other country. Can search to IP address
                urlStr.append("https://www.afisha.ru/msk/schedule_cinema_product/")
                        .append(filmIdFromAfisha)
                        .append("/page")
                        .append(i);

                Document HTMLdoc = pliProxyServer.getHttpDocumentFromInternet(urlStr.toString());

                cinemaList.addAll(pliParserAfisha.parse(HTMLdoc));
            }
            /*
            Какая то логика, которая будет выбирать подходящий сеанс. Возможно вынести это в отдельный метод.
            Пока передам просто 1 элемент из листа
             */
            //pliParserKinopoisk.getUrlForBuyTicketsFromInternet(cinemaList.get(0), cinemaList.get(0).getMovieList().get(0));
        }
        logger.info("End of method getCinemaListWithMovie() at " + LocalDateTime.now() + " - with result.size(): " + cinemaList.size());
        return cinemaList;
    }

    @Override
    public List<Cinema> updateCinemaListFromTypeShow(List<Cinema> cinemaList, String type) {
        logger.info("Start method updateCinemaListFromTypeShow() at " + LocalDateTime.now());
        //TODO: проверка на тип. Если пустой, то оставлять весь список, если несколько типов, то проверку по всем.
        List<Cinema> cinemaListNew = new ArrayList<>();
        List<Movie> movieListOld;
        List<Movie> movieListNew;
        for (Cinema cinema : cinemaList) {
            movieListNew = new ArrayList<>(); //Сюда добавляем только те фильмы, что удовлетворяют условию
            movieListOld = cinema.getMovieList();
            for (Movie movie : movieListOld) {
                if (movie.getSession().getTypeOfMovie().contains(type)) {
                    movieListNew.add(movie);
                }
            }
            cinema.getMovieList().removeAll(movieListOld);
            cinema.setMovieList(movieListNew);
            if (movieListNew.size() != 0) {
                cinemaListNew.add(cinema);
            }
        }
        logger.info("End of method updateCinemaListFromTypeShow() at " + LocalDateTime.now() + " - with result.size(): " + cinemaListNew.size());
        return cinemaListNew;
    }

    @Override
    public List<Cinema> updateCinemaListFromTimeShow(List<Cinema> cinemaList, String userTime) {
        logger.info("Start method updateCinemaListFromTimeShow() at " + LocalDateTime.now());
        List<Cinema> cinemaListNew = new ArrayList<>();
        List<Movie> movieListOld;
        List<Movie> movieListNew;
        for (Cinema cinema : cinemaList) {
            movieListNew = new ArrayList<>();
            movieListOld = cinema.getMovieList();
            for (Movie movie : movieListOld) {
                if (isRrquiredPeriod(userTime, movie.getSession().getTimeOfShow())) {
                    movieListNew.add(movie);
                }
            }
            cinema.getMovieList().removeAll(movieListOld);
            cinema.setMovieList(movieListNew);
            if (movieListNew.size() != 0) {
                cinemaListNew.add(cinema);
            }
        }
        logger.info("End of method updateCinemaListFromTimeShow() at " + LocalDateTime.now() + " - with result.size(): " + cinemaListNew.size());
        return cinemaListNew;
    }

    @Override
    public List<Cinema> updateCinemaListFromPlace(List<Cinema> cinemaList, String place) {
        List<Cinema> cinemaListNew = new ArrayList<>();
        for (Cinema cinema : cinemaList) {
            if (StringUtils.contains(cinema.getCinemaUnderground(), place)) {
                cinemaListNew.add(cinema);
            }
        }
        return cinemaListNew;
    }

    /**
     * Метод, который проверяет, подходит ли время сеанса пользователю, в том случае, если пользователь
     * ввел время сеанса (обязательное поле) А так же идет проверка разницы времени (от сеанса, до текущего)
     * И если до сеанса остается меньше 59 минут, то он не предлагается, пока не будет введена переменная isNow, которая уменьшит лимит
     * @param userTime - Интересующее пользователя время
     * @param movieTime - Время сеанса кино
     * @return - true, если время сеанса нам подходит и false - если нет.
     */
    private boolean isRrquiredPeriod (String userTime, String movieTime) {
        logger.info("Start method isRrquiredPeriod() at " + LocalDateTime.now());
        //TODO: сделать возмость "сейчас", чтобы можно было купить билет на ближайший сеанс.
        Matcher mUser = PATTERN_TIME.matcher(userTime);
        Matcher mMovie = PATTERN_TIME.matcher(movieTime);
        if (mUser.matches() && mMovie.matches()) {
            String hourStringUser = mUser.group(1);
            String minuteStringUser = mUser.group(2);
            String hourStringMovie = mMovie.group(1);
            String minuteStringMovie = mMovie.group(2);

            int hourUser = Integer.parseInt(hourStringUser);
            int minuteUser = Integer.parseInt(minuteStringUser);
            int hourMovie = Integer.parseInt(hourStringMovie);
            int minuteMovie = Integer.parseInt(minuteStringMovie);


            LocalTime localUserTime = LocalTime.of(hourUser, minuteUser);
            LocalTime localMovieTime = LocalTime.of(hourMovie, minuteMovie);
            LocalTime currentTime = LocalTime.now();
            LocalTime deltaCurrentAndMovie = localMovieTime.minusHours(currentTime.getHour()).minusMinutes(currentTime.getMinute());
            LocalTime deltaUserAndMovie = localMovieTime.minusHours(localUserTime.getHour()).minusMinutes(localUserTime.getMinute());

            if (deltaCurrentAndMovie.getHour() < 1 && deltaCurrentAndMovie.getMinute() < 59) { //Если до фильма осталось меньше 59 минут, вернет false. Тут будет актуальна переменная isNow
                return false;
            } else {
                if (deltaUserAndMovie.getHour() == 1 && deltaUserAndMovie.getMinute() <= 20) {
                    return true;
                } else if (deltaUserAndMovie.getHour() == 0 && deltaUserAndMovie.getMinute() <= 59) {
                    return true;
                } else if (deltaUserAndMovie.getHour() == 23 && deltaUserAndMovie.getMinute() >= 30) {
                    return true;
                } else {
                    return false;
                }
            }

        }
        return false;
    }


    //--------------------------Injection part------------------------//

    PliParser pliParserAfisha;
    PliParserKinopoisk pliParserKinopoisk;
    private PliProxyServer pliProxyServer;

    @Inject
    public void setPliProxyServer(PliProxyServer pliProxyServer) {
        this.pliProxyServer = pliProxyServer;
    }

    @Inject
    public void setPlParser (PliParser pliParser) {
        this.pliParserAfisha = pliParser;
    }

    @Inject
    public void setPliParserKinopoisk (PliParserKinopoisk pliParserKinopoisk1) {
        this.pliParserKinopoisk = pliParserKinopoisk1;
    }

    public static void main(String[] args) throws IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate ld = LocalDate.now();
        LocalTime current = LocalTime.of(17, 13);
        LocalTime movie = LocalTime.of(15, 30);
        movie = movie.minusHours(current.getHour());

        PlUserLogicFromInternet p = new PlUserLogicFromInternet();

        System.out.println(p.isRrquiredPeriod("23:45", "00:40"));
        PlUserLogicFromInternet pl = new PlUserLogicFromInternet();
        PliParser plPars = new PlParserAfisha();
        plPars.createUrlFromQuery("Человек паук вдали от дома");
        List<Cinema> cl = pl.getCinemaListWithMovie("Человек паук вдали от дома");
        List<Cinema> cl_time = pl.updateCinemaListFromTimeShow(cl, "22:30");
        List<Cinema> cl_type = pl.updateCinemaListFromTypeShow(cl_time, "2D");
        List<Cinema> cl_place = pl.updateCinemaListFromPlace(cl_time, "Аннино");
        System.out.println(cl_place.size());
    }
}
