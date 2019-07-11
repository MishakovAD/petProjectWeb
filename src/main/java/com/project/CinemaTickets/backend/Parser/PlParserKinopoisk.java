package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.CinemaEntity.Session;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PlParserKinopoisk implements PliParserKinopoisk {
    public static String HELPER_FOR_QUERY_KINOPOISK = "купить билеты kinopoisk.ru ";
    public static String HELPER_FOR_BUY_TICKETS = "https://tickets.widget.kinopoisk.ru/w/sessions/";
    public String CITY_MOSCOW = "Москва";
    public static Pattern PATTERN_CINEMA_KINOPOISK = Pattern.compile("(https://www.kinopoisk.ru/afisha/city/\\d+/cinema/[a-z0-9A-Zа-яА-Я -]+/?)");

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
        StringBuffer urlForBuyTickets = new StringBuffer(HELPER_FOR_BUY_TICKETS);
        String urlForCinema = createUrlFromQuery(cinema.getName());

        Document cinemaDocument = Jsoup.connect(urlForCinema)
                .userAgent("Mozilla/5.0 Chrome/75.0.3770.100 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
//TODO: обязательно добавить проверку на тип сеанса, если пользователь скажет. что это важно! Либо сделать так, чтобы sortedCinemaListFromTypeShow() метод выполнял ее.
        Elements elements = cinemaDocument.select("div.cinema-seances-page__seances");
        for (Element element : elements.select("div.schedule-item.schedule-item_type_film")) {
            if (StringUtils.equalsAnyIgnoreCase(movie.getName(), element.select("a.link.schedule-film__title").text())) {
                for (Element timeElement : element.select("span.schedule-item__session-button.schedule-item__session-button_active.js-yaticket-button")) {
                    if (StringUtils.equalsAnyIgnoreCase(timeElement.text(), movie.getSession().getTimeOfShow())) {
                        urlForBuyTickets.append(timeElement.attr("data-session-id"));
                        return urlForBuyTickets.toString();
                    }
                }
            }
        }

        return "Сеанс не найден.";
    }

    @Override
    public String createURLFromQueryWithGoogle(String url) throws IOException {
        String urlFromGoogle = null;
        Document googleHTMLdoc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 Chrome/75.0.3770.100 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
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

        return urlFromGoogle;
    }

    @Override
    public String createURLFromQueryWithYandex(String url) throws IOException {
        String urlFromYandex = null;
        Document googleHTMLdoc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 Chrome/75.0.3770.100 Safari/537.36")
                .referrer("http://www.google.ru")
                .get();
        Elements elements = googleHTMLdoc.select("ul.serp-list.serp-list_left_yes");
        for (Element element : elements.select("li.serp-item").select("a.link.link_theme_outer.path__item.i-bem")) {
            urlFromYandex = element.getElementsByTag("a").attr("href");
            if (PATTERN_CINEMA_KINOPOISK.matcher(urlFromYandex).matches()){
                int firstIndex = urlFromYandex.indexOf("http");
                urlFromYandex = urlFromYandex.substring(firstIndex);
                break;
            }
            urlFromYandex = null;
        }

        return urlFromYandex;
    }

    @Override
    public String createUrlFromQuery(String queryForUrl) throws IOException {
        String urlFromGoogle = null;
        String urlFromYandex = null;

        StringBuffer urlQueryForYandex = new StringBuffer();
        String[] wordsFromQuery = queryForUrl.split(" ");
        urlQueryForYandex.append("https://yandex.ru/search/?lr=213&text=");
        for (String word : wordsFromQuery) {
            urlQueryForYandex.append(word + "+");        }
        urlQueryForYandex.append(HELPER_FOR_QUERY_KINOPOISK);
        urlFromYandex = createURLFromQueryWithYandex(urlQueryForYandex.toString());

        if (urlFromYandex == null) {
            StringBuffer urlQueryForGoogle = new StringBuffer();
            urlQueryForGoogle.append("https://www.google.com/search?q=");
            for (String word : wordsFromQuery) {
                urlQueryForGoogle.append(word + "+");
            }
            urlQueryForGoogle.append(HELPER_FOR_QUERY_KINOPOISK);
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
        ses.setTimeOfShow("00:15");
        ses.setTypeOfMovie("2D");
        mov.setSession(ses);
        System.out.println(p.getUrlForBuyTickets(cin, mov));
        //System.out.println(p.createUrlFromQuery("космик"));
    }
}
