package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.ProxyServer.PliProxyServer;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSession;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.coolection.CinemaMovieSessionObj;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.project.CinemaTickets.backend.constants.Constants.HELPER_FOR_BUY_TICKETS;
import static com.project.CinemaTickets.backend.constants.Constants.HELPER_FOR_QUERY_KINOPOISK;
import static com.project.CinemaTickets.backend.constants.Constants.PATTERN_CINEMA_KINOPOISK;

@Component
public class PlParserKinopoisk implements PliParserKinopoisk {
    int counterUrlForBuyTickets = 0;
    private Logger logger = LoggerFactory.getLogger(PlParserKinopoisk.class);

    @Override
    public String getUrlForBuyTicketsFromInternet(Cinema cinema, Movie movie) throws IOException {
        logger.info("Start method getUrlForBuyTicketsFromInternet() at " + LocalDateTime.now());
        counterUrlForBuyTickets++;
        if (counterUrlForBuyTickets > 25) {
            logger.error("Method getUrlForBuyTicketsFromInternet() failed! " + LocalDateTime.now());
            return "Ссылка не найдена. Повторите попытку позже.";
        }
        StringBuffer urlForBuyTickets = new StringBuffer(HELPER_FOR_BUY_TICKETS);
        String urlForCinema = createUrlFromQuery(cinema.getCinemaName());
        if (urlForCinema == null || urlForCinema.isEmpty()) {
            getUrlForBuyTicketsFromInternet(cinema, movie);
        }
        Document cinemaDocument = pliProxyServer.getHttpDocumentFromInternet(urlForCinema);
        logger.info("############# Документ получен для кинотеатра " + cinema.getCinemaName());
        Elements elements = cinemaDocument.select("div.cinema-seances-page__seances");
        for (Element element : elements.select("div.schedule-item.schedule-item_type_film")) {
            if (StringUtils.equalsAnyIgnoreCase(movie.getMovieName(), element.select("a.link.schedule-film__title").text())) {
                for (Element timeElement : element.select("span.schedule-item__session-button.schedule-item__session-button_active.js-yaticket-button")) {
                    if (StringUtils.equalsAnyIgnoreCase(timeElement.text(), movie.getSession().getTimeOfShow())) {
                        urlForBuyTickets.append(timeElement.attr("data-session-id"));
                        logger.info("Method getUrlForBuyTicketsFromInternet() finished successful at " + LocalDateTime.now());
                        return urlForBuyTickets.toString();
                    }
                }
            }
        }
        logger.info("Method getUrlForBuyTicketsFromInternet() failed! " + LocalDateTime.now() + " - Session not found!");
        return "Сеанс не найден.";
    }

    @Override
    public Cinema getCinemaFromDocument(Document document) {
        logger.info("Start method getCinemaFromDocument() at " + LocalDateTime.now() + " in PlParserKinopoisk.class");
        Cinema cinema = new Cinema();
        String cinemaName = document.select("h1.cinema-header__title").text();
        String cinemaAddress = document.select("div.cinema-header__address").text();
        String cinemaUnderground = document.select("div.cinema-header__metro").text();
        String infoAboutCinema = "Рэйтинг: " + document.select("span.cinema-header__rating.cinema-header__rating_type_positive").text();
        String urlToKinopoisk = document.location(); //TODO: придумать, как можно исключить добавление ссылок по датам и прочее. Только, чтобы добавлялась ссылка на кинотеатр с расписанием на сегодня

        if (cinemaName != null) {
            cinema.setCinemaName(cinemaName);
        } else {
            cinema.setCinemaName("Не найдено");
        }
        if (cinemaAddress != null) {
            cinema.setCinemaAddress(cinemaAddress);
        } else {
            cinema.setCinemaAddress("Не найдено");
        }
        if (cinemaUnderground != null) {
            cinema.setCinemaUnderground(cinemaUnderground);
        } else {
            cinema.setCinemaUnderground("Не найдено");
        }
        if (infoAboutCinema != null) {
            cinema.setInfoAboutCinema(infoAboutCinema);
        } else {
            cinema.setInfoAboutCinema("Не найдено");
        }
        if (urlToKinopoisk != null) {
            cinema.setUrlToKinopoisk(urlToKinopoisk);
        } else {
            cinema.setUrlToKinopoisk("Не найдено");
        }
        List<Movie> movies = getMovieListFromDocument(cinema, document);
        if (movies != null) {
            cinema.setMovieList(movies);
        }
        logger.info("End of method getCinemaFromDocument() at " + LocalDateTime.now() + " in PlParserKinopoisk.class");
        return cinema;
    }

    @Override
    public List<Movie> getMovieListFromDocument(Cinema cinema, Document document) {
        logger.debug("Start method getMovieListFromDocument() at " + LocalDateTime.now() + " in PlParserKinopoisk.class");
        List<Movie> movieList = new ArrayList<>();
        Movie movie;
        Elements movieElements = document.select("div.schedule-item.schedule-item_type_film");
        for (Element movieElement : movieElements) {
            movie = getMovieFromElement(cinema, movieElement);
            movieList.add(movie);
        }
        return movieList;
    }

    @Override
    public Movie getMovieFromElement(Cinema cinema, Element element) {
        logger.debug("Start method getMovieFromElement() at " + LocalDateTime.now() + " in PlParserKinopoisk.class");
        Movie movie = new Movie();
        String movieName = element.select("a.link.schedule-film__title").text();
        String movieRating = element.select("span.schedule-film__rating-value.schedule-film__rating-value_type_neutral").text();

        if (movieName != null) {
            movie.setMovieName(movieName);
        } else {
            movie.setMovieName("Не найдено");
        }
        if (movieRating != null) {
            movie.setMovieRating(movieRating);
        } else {
            movie.setMovieRating("Не найдено");
        }
        List<Session> sessionList = getSessionListFromElement(cinema, movie, element);
        if (sessionList != null) {
            movie.setSessionList(sessionList);
        }

        return movie;
    }

    @Override
    public List<Session> getSessionListFromElement(Cinema cinema, Movie movie, Element element) {
        //TODO: если билеты продаются только в кинотеатре, то сеанс находится неверно, так как неверно выбираются позиции. Нужно что то придумать с этим.
        // Смысл искать кинотеатры не Москвы для проверки. Там для них идет выборка span.schedule-item__session-button вместо -wrapper на конце. (такая выборка без цен)
        logger.debug("Start method getSessionListFromElement() at " + LocalDateTime.now() + " in PlParserKinopoisk.class");
        List<Session> sessionList = new ArrayList<>();
        Session session;
        Elements sessionElements = element.select("div.schedule-item__formats-row");
        for (Element sessionElement : sessionElements) {
            for (Element sessionRootElement : sessionElement.select("span.schedule-item__session-button-wrapper")) {
                session = getSessionFromElement(sessionRootElement);

                String typeMovie = sessionElement.select("span.schedule-item__formats-format").text();
                if (typeMovie != null) {
                    session.setTypeOfShow(typeMovie);
                } else {
                    session.setTypeOfShow("Не найдено");
                }

                sessionList.add(session);
            }
        }
        return sessionList;
    }

    @Override
    public Session getSessionFromElement(Element element) {
        logger.debug("Start method getSessionFromElement() at " + LocalDateTime.now() + " in PlParserKinopoisk.class");
        String pageUrl = element.baseUri();
        Session session = new Session();
        String timeMovie = element.select("span.schedule-item__session-button.schedule-item__session-button_active.js-yaticket-button").text();
        if (StringUtils.isEmpty(timeMovie)) {
            timeMovie = element.select("span.schedule-item__session-button").text();
        }


        String price = element.select("span.schedule-item__price").text();
        String urlForBuyTickets = HELPER_FOR_BUY_TICKETS + element
                .select("span.schedule-item__session-button.schedule-item__session-button_active.js-yaticket-button")
                .attr("data-session-id");
        String movieDate;
        if (pageUrl.contains("day_view")) {
            movieDate = pageUrl.substring(pageUrl.indexOf("day_view/") + 9);
        } else {
            movieDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if(!StringUtils.isEmpty(timeMovie)) {
            session.setTimeOfShow(timeMovie);
        } else {
            session.setTimeOfShow("Не найдено");
        }
        if (!StringUtils.isEmpty(price)) {
            session.setPrice(price);
        } else {
            session.setPrice("Билеты продаются в кассах кинотеатра.");
        }
        if (urlForBuyTickets != null) {
            session.setUrl(urlForBuyTickets);
        } else {
            session.setUrl("Не найдено");
        }
        if (movieDate != null) {
            session.setSessionDate(movieDate.replaceAll("/", ""));
        } else {
            session.setSessionDate("Не найдено");
        }

        return session;
    }


    @Override
    public String createURLFromQueryWithGoogle(String url) throws IOException {
        logger.info("Start method createURLFromQueryWithGoogle() at " + LocalDateTime.now() + " - with url: " + url);
        String urlFromGoogle = null;
        Document googleHTMLdoc = pliProxyServer.getHttpDocumentFromInternet(url);
        Elements elements = googleHTMLdoc.select("div#main");
        for (Element element : elements.select("div.ZINbbc.xpd.O9g5cc.uUPGi")) {
            urlFromGoogle = element.getElementsByTag("a").attr("href");
            if (PATTERN_CINEMA_KINOPOISK.matcher(urlFromGoogle).matches()){
                int firstIndex = urlFromGoogle.indexOf("http");
                int lastIndex = urlFromGoogle.indexOf("/&");
                urlFromGoogle = urlFromGoogle.substring(firstIndex, lastIndex+1);
                break;
            }
            urlFromGoogle = null;
        }
        logger.info("End of method createURLFromQueryWithGoogle() at " + LocalDateTime.now() + " - with result: " + urlFromGoogle);
        return urlFromGoogle;
    }

    @Override
    public String createURLFromQueryWithYandex(String url) throws IOException {
        logger.info("Start method createURLFromQueryWithYandex() at " + LocalDateTime.now() + " - with query: " + url);
        String urlFromYandex = null;
        Document yandexHTMLdoc = pliProxyServer.getHttpDocumentFromInternet(url);
        Elements elements = yandexHTMLdoc.select("ul.serp-list.serp-list_left_yes");
        for (Element element : elements.select("li.serp-item").select("a.link.link_theme_outer.path__item.i-bem")) {
            urlFromYandex = element.getElementsByTag("a").attr("href");
            if (PATTERN_CINEMA_KINOPOISK.matcher(urlFromYandex).matches()){
                int firstIndex = urlFromYandex.indexOf("http");
                urlFromYandex = urlFromYandex.substring(firstIndex);
                break;
            }
            urlFromYandex = null;
        }
        logger.info("End of method createURLFromQueryWithYandex() at " + LocalDateTime.now() + " - with result: " + urlFromYandex);
        return urlFromYandex;
    }

    @Override
    public String createUrlFromQuery(String queryForUrl) throws IOException {
        logger.info("Start method createURLFromQuery() at " + LocalDateTime.now() + " - with queryForUrl: " + queryForUrl);
        String urlFromGoogle = null;
        String urlFromYandex = null;

        StringBuffer urlQueryForYandex = new StringBuffer();
        String[] wordsFromQuery = queryForUrl.split(" ");
        urlQueryForYandex.append("https://yandex.ru/search/?lr=213&text=");
        for (String word : wordsFromQuery) {
            urlQueryForYandex.append(word + "+");
        }
        for (String words : HELPER_FOR_QUERY_KINOPOISK.split(" ")) {
            urlQueryForYandex.append(words + "+");
        }
        System.out.println("##createUrlFromQuery = " + urlQueryForYandex.toString());
        urlFromYandex = createURLFromQueryWithYandex(urlQueryForYandex.toString());

        if (urlFromYandex == null) {
            StringBuffer urlQueryForGoogle = new StringBuffer();
            urlQueryForGoogle.append("https://www.google.com/search?q=");
            for (String word : wordsFromQuery) {
                urlQueryForGoogle.append(word + "+");
            }
            for (String words : HELPER_FOR_QUERY_KINOPOISK.split(" ")) {
                urlQueryForGoogle.append(words + "+");
            }
            urlFromGoogle = createURLFromQueryWithGoogle(urlQueryForGoogle.toString());
        }

        return urlFromYandex != null ? urlFromYandex : urlFromGoogle;
    }

    public static void main(String[] args) throws IOException {
        PlParserKinopoisk p = new PlParserKinopoisk();
        Cinema cin = new Cinema();
        cin.setCinemaName("Космик");
        Movie mov = new Movie();
        mov.setMovieName("Человек-паук: Вдали от дома");
        Session ses = new Session();
        ses.setTimeOfShow("22:20");
        ses.setTypeOfShow("2D");
        mov.setSession(ses);


        Document doc = Jsoup.connect("https://www.kinopoisk.ru/afisha/city/1/cinema/280891/day_view/2019-07-20/")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .get();
        System.out.println(p.getCinemaFromDocument(doc));
        //System.out.println(p.getUrlForBuyTicketsFromInternet(cin, mov));
        //System.out.println(p.createUrlFromQuery("космик"));
    }

    private PliProxyServer pliProxyServer;

    @Inject
    public void setPliProxyServer(PliProxyServer pliProxyServer) {
        this.pliProxyServer = pliProxyServer;
    }
}
