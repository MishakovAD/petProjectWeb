package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.CinemaEntity.Session;
import com.project.CinemaTickets.backend.ProxyServer.PliProxyServer;
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
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PlParserKinopoisk implements PliParserKinopoisk {
    int counterUrlForBuyTickets = 0;
    public static String HELPER_FOR_QUERY_KINOPOISK = "купить билеты kinopoisk.ru afisha";
    public static String HELPER_FOR_BUY_TICKETS = "https://tickets.widget.kinopoisk.ru/w/sessions/";
    public String CITY_MOSCOW = "Москва";
    public static Pattern PATTERN_CINEMA_KINOPOISK = Pattern.compile("(https://www.kinopoisk.ru/afisha/city/\\d+/cinema/[a-z0-9A-Zа-яА-Я -]+/?)");

    private Logger logger = LoggerFactory.getLogger(PlParserKinopoisk.class);

    /**
     * Поисковым запросом мы сразу должны получить страницу с кинотеатром,
     * где по элементам ищем название нашего фильма,
     * а затем нужный сеанс и берем оттуда сессию и вставляем в ссылку
     * Другой вариант: Сразу идти на сайт кинопоиска
     * и там в поисковый запрос вставлять название, но это дольше. Не подходит.
     * @param cinema
     * @param movie
     * @return
     * @throws IOException
     */
    @Override
    public String getUrlForBuyTickets(Cinema cinema, Movie movie) throws IOException {
        logger.info("Start method getUrlForBuyTickets() at " + LocalDateTime.now());
        counterUrlForBuyTickets++;
        if (counterUrlForBuyTickets > 25) {
            logger.error("Method getUrlForBuyTickets() failed! " + LocalDateTime.now());
            return "Ссылка не найдена. Повторите попытку позже.";
        }
        StringBuffer urlForBuyTickets = new StringBuffer(HELPER_FOR_BUY_TICKETS);
        String urlForCinema = createUrlFromQuery(cinema.getName());
        if (urlForCinema == null || urlForCinema.isEmpty()) {
            getUrlForBuyTickets(cinema, movie);
        }
        Document cinemaDocument = pliProxyServer.getHttpDocumentFromInternet(urlForCinema);
        logger.info("############# Документ получен для кинотеатра " + cinema.getName());
//TODO: обязательно добавить проверку на тип сеанса, если пользователь скажет. что это важно! Либо сделать так, чтобы sortedCinemaListFromTypeShow() метод выполнял ее.
        Elements elements = cinemaDocument.select("div.cinema-seances-page__seances");
        for (Element element : elements.select("div.schedule-item.schedule-item_type_film")) {
            if (StringUtils.equalsAnyIgnoreCase(movie.getName(), element.select("a.link.schedule-film__title").text())) {
                for (Element timeElement : element.select("span.schedule-item__session-button.schedule-item__session-button_active.js-yaticket-button")) {
                    if (StringUtils.equalsAnyIgnoreCase(timeElement.text(), movie.getSession().getTimeOfShow())) {
                        urlForBuyTickets.append(timeElement.attr("data-session-id"));
                        logger.info("Method getUrlForBuyTickets() finished successful at " + LocalDateTime.now());
                        return urlForBuyTickets.toString();
                    }
                }
            }
        }
        logger.info("Method getUrlForBuyTickets() failed! " + LocalDateTime.now() + " - Session not found!");
        return "Сеанс не найден.";
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

    public Cinema getCinemaFromElement(Element element) throws IOException {
        return null;
    }

    public List<Session> getSessionFromElement(Element element) throws IOException {
        return null;
    }

    public static void main(String[] args) throws IOException {
        PlParserKinopoisk p = new PlParserKinopoisk();
        Cinema cin = new Cinema();
        cin.setName("Космик");
        Movie mov = new Movie();
        mov.setName("Человек-паук: Вдали от дома");
        Session ses = new Session();
        ses.setTimeOfShow("22:20");
        ses.setTypeOfMovie("2D");
        mov.setSession(ses);
        System.out.println(p.getUrlForBuyTickets(cin, mov));
        //System.out.println(p.createUrlFromQuery("космик"));
    }

    private PliProxyServer pliProxyServer;

    @Inject
    public void setPliProxyServer(PliProxyServer pliProxyServer) {
        this.pliProxyServer = pliProxyServer;
    }
}
