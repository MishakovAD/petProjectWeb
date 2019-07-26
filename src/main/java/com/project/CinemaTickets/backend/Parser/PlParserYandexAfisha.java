package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.CinemaEntity.Timetable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//TODO: Сейчас сделаем просто получение ссылки для фильма, который выбрали, в последующем нужно будет сделать отдельный парсер для ya.afisha
public class PlParserYandexAfisha extends PlParserAfisha implements PliParser {
    public static String HELPER_FOR_QUERY_YANDEX_AFISHA = "купить билеты afisha.yandex.ru ";
    public String CITY_MOSCOW = "Москва";
    public static Pattern PATTERN_CINEMA_YANDEX_AFISHA = Pattern.compile("(https://afisha.yandex.ru/moscow/cinema/places/[a-z0-9A-Zа-яА-Я -]+)"); //возможно в конце нужен символ /

    public Document getHTMLDocumentOfYandexAfisha (String queryCinemaAndMovie) throws IOException {
        String url = createUrlFromQuery(queryCinemaAndMovie);

        Document searchDocument = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .referrer("https://afisha.yandex.ru/moscow/cinema/chelovek-pauk-vdali-ot-doma")
                .get();



        return null;
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
            if (PATTERN_CINEMA_YANDEX_AFISHA.matcher(urlFromGoogle).matches()){
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
            PATTERN_CINEMA_YANDEX_AFISHA.matcher("").matches();
            if (PATTERN_CINEMA_YANDEX_AFISHA.matcher(urlFromYandex).matches()){
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
        urlQueryForYandex.append(HELPER_FOR_QUERY_YANDEX_AFISHA + CITY_MOSCOW);
        urlFromYandex = createURLFromQueryWithYandex(urlQueryForYandex.toString());

        if (urlFromYandex == null) {
            StringBuffer urlQueryForGoogle = new StringBuffer();
            urlQueryForGoogle.append("https://www.google.com/search?q=");
            for (String word : wordsFromQuery) {
                urlQueryForGoogle.append(word + "+");
            }
            urlQueryForGoogle.append(HELPER_FOR_QUERY_YANDEX_AFISHA + CITY_MOSCOW);
            urlFromGoogle = createURLFromQueryWithGoogle(urlQueryForGoogle.toString());
        }

        return urlFromYandex != null ? urlFromYandex : urlFromGoogle;
    }

    public Cinema getInformationAboutCinema (String nameCinema) throws IOException {
        Cinema cinema = new Cinema();
        String urlCinemaToAfishaYandex = createUrlFromQuery(nameCinema);

        Document cinemaInfoHTML = Jsoup.connect(urlCinemaToAfishaYandex)
                .userAgent("Chrome/75.0.3770.100 Safari/537.36")
                .referrer("http://www.google.ru")
                .get();

        String addressCinema = cinemaInfoHTML.select("div.place-heading__address").text();

        cinema.setCinemaName(nameCinema);
        cinema.setCinemaAddress(addressCinema);
        cinema.setUrlToYandexAfisha(urlCinemaToAfishaYandex);

        List<Movie> movieList = new ArrayList<>();
        Movie movie;
        Session session;
        Timetable timetable;
        Elements timetableElements = cinemaInfoHTML.select("div.schedule-pcinema-list__inner");
        for (Element element : timetableElements) {
            for (Element movieElement : element.select("h3.schedule-event__title")) {
                movie = new Movie();
                timetable = new Timetable();
                movie.setMovieName(movieElement.select("h3.schedule-event__title").text());
                movie.setCinema(cinema);
                for (Element sessionElement : element.select("div.schedule-cinema-item-sessions.schedule-grid__subgroup")){
                    for (Element rootSessionElement : sessionElement.select("div.schedule-sessions")) {
                        session = new Session();
                        session.setTypeOfShow(sessionElement.select("div.schedule-format").text());
                        session.setTimeOfShow(rootSessionElement.select("span.button2__text").text());
                        session.setUrl(rootSessionElement
                                .getElementsByAttributeValue("class", "yaticket i-stat__click i-bem yaticket_js_inited")
                                .attr("href"));
                        session.setTimeOfShow(rootSessionElement.getElementsByAttributeValue("class", "button2__text").text());
                        session.setPrice(rootSessionElement.getElementsByAttributeValue("class", "atooltip__content").text());
                        timetable.setSession(session);
                    }
                }
                movieList.add(movie);
            }
        }
        cinema.setMovieList(movieList);
            System.out.println("В кинотеатре " + cinema.getCinemaName() + " можно увидеть следующие фильмы: ");
            for (Movie mov : cinema.getMovieList()) {
                System.out.println(mov.toString());
            }

        return null;
    }

    public static void main(String[] args) throws IOException {
        PlParserYandexAfisha p = new PlParserYandexAfisha();
        //System.out.println(p.createUrlFromQuery("человек паук вдали от дома"));
        p.getInformationAboutCinema("5 звезд на павелецкой");

    }
}
