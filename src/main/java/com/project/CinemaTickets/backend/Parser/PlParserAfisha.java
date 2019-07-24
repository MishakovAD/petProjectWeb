package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Timetable;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.ProxyServer.PlProxyServer;
import com.project.CinemaTickets.backend.ProxyServer.PliProxyServer;
import com.project.CinemaTickets.backend.UserLogic.PlUserLogicFromInternet;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PlParserAfisha implements PliParser {
    public static String HELPER_FOR_QUERY_AFISHA = "afisha.ru ";
    public String CITY_MOSCOW = "Москва";
    public static Pattern PATTERN_CINEMA_AFISHA_FIRST = Pattern.compile("(https://www.afisha.ru/movie/[0-9]+/?)");
    public static Pattern PATTERN_CINEMA_AFISHA_SECOND = Pattern.compile("(https://www.afisha.ru/\\w+/schedule_cinema_product/[0-9]+/?)");

    private Logger logger = LoggerFactory.getLogger(PlParserAfisha.class);
    private int counterOfCreatingUrl = 0;

    @Override
    public List<Cinema> parse(Document HTMLdoc) throws IOException {
        logger.info("Start method parse() at " + LocalDateTime.now());
        List<Movie> movieTimetableList = new ArrayList<>();
        List<Cinema> cinemaList = new ArrayList<>();
        Movie movie;
        Cinema cinema;

        String nameMovie = HTMLdoc.select("div.title__name").text();
        String ratingMovie = HTMLdoc.select("div.rating-control__average-rating-value").text();
        String dateFromMovie = nameMovie.substring(nameMovie.indexOf(", ")+2); //Можно брать из ссылки. Если переключить в расписании на следующую дату, то будет видно. Или из nameMovie
        nameMovie = nameMovie.substring(nameMovie.indexOf("«")+1, nameMovie.indexOf("»"));

        Elements elements = HTMLdoc.select("ul.listSchedule___11A9v");

        for (Element element : elements.select("li")) {
            Elements rootElements = element.getElementsByAttributeValue("class", "unit__schedule-row");
            for (Element rootElement : rootElements) {
                cinema = getCinemaFromElement(rootElement);
                Timetable timetable = new Timetable(getSessionFromElement(rootElement));
                for (Session session : timetable.getTimetable()) {
                    movie = new Movie();
                    movie.setMovieName(nameMovie);
                    movie.setMovieRating(ratingMovie);
                    movie.setMovieDate(dateFromMovie);
                    movie.setCinema(cinema);
                    movie.setSession(session);
                    movieTimetableList.add(movie);
                }
                cinema.setMovieList(movieTimetableList);
                movieTimetableList = new ArrayList<>();
                cinemaList.add(cinema);
            }
        }
        logger.info("End of method createURLFromQueryWithGoogle() at " + LocalDateTime.now() + " - with result.size(): " + cinemaList.size());
        return cinemaList;
    }

    @Override
    public int counterOfPage(String filmId) throws IOException {
        logger.info("Start method counterOfPage() at " + LocalDateTime.now());
        int counterOfPage = 0;
        StringBuffer urlStr = new StringBuffer();
        //TODO: At this time, this work only for Moscow. At future change "msk" on other country.
        urlStr.append("https://www.afisha.ru/")
                .append("msk") //CHANGE THERE
                .append("/schedule_cinema_product/")
                .append(filmId)
                .append("/page");

        for (int i = 1; ; i++){
            StringBuffer correctUrl = new StringBuffer(urlStr);
            correctUrl.append(i);
            URL url = new URL(correctUrl.toString());
            URLConnection urlConnection = url.openConnection();
            if (urlConnection.getHeaderField(0).contains("404")) {
                counterOfPage = i - 1;
                break;
            } else if (i > 777) {
                break;
            }
        }
        logger.info("End of method counterOfPage() at " + LocalDateTime.now() + " - with result= " + counterOfPage);
        return counterOfPage;
    }

    //TODO: Сделать отдельную проверку на наличие определенных фраз(купить билет). В идеале это будет на нейронной сети.
    //TODO: А так же необходимо для обоих методов сделать проверку на полученную ссылку согласно паттерну, чтобы не было левых ссылок
    @Override
    public String createURLFromQueryWithGoogle(String query) throws IOException {
        logger.info("Start method createURLFromQueryWithGoogle() at " + LocalDateTime.now() + " - with query: " + query);
        String urlFromGoogle = null;

        Document googleHTMLdoc = pliProxyServer.getHttpDocumentFromInternet(query);
//        logger.info("#####################################################################");
//        logger.info("##############" + googleHTMLdoc);
//        logger.info("#####################################################################");
        Elements elements = googleHTMLdoc.select("div#main");
        for (Element element : elements.select("div.ZINbbc.xpd.O9g5cc.uUPGi")) {
            urlFromGoogle = element.getElementsByTag("a").attr("href");
            if (PATTERN_CINEMA_AFISHA_FIRST.matcher(urlFromGoogle).matches() ||
                    PATTERN_CINEMA_AFISHA_SECOND.matcher(urlFromGoogle).matches()){
                int firstIndex = urlFromGoogle.indexOf("http");
                int lastIndex = urlFromGoogle.indexOf("/&");
                urlFromGoogle = urlFromGoogle.substring(firstIndex, lastIndex+1);
                break;
            }
        }
        logger.info("End of method createURLFromQueryWithGoogle() at " + LocalDateTime.now() + " - with result: " + urlFromGoogle);
        return urlFromGoogle;
    }

    @Override
    public String createURLFromQueryWithYandex(String query) throws IOException {
        logger.info("Start method createURLFromQueryWithYandex() at " + LocalDateTime.now() + " - with query: " + query);
        String urlFromYandex = null;

        Document yandexHTMLdoc = pliProxyServer.getHttpDocumentFromInternet(query);
//        logger.info("#####################################################################");
//        logger.info("##############" + yandexHTMLdoc);
//        logger.info("#####################################################################");
        Elements elements = yandexHTMLdoc.select("ul.serp-list.serp-list_left_yes");
        for (Element element : elements.select("li.serp-item").select("a.link.link_theme_outer.path__item.i-bem")) {
            urlFromYandex = element.getElementsByTag("a").attr("href");
            if (PATTERN_CINEMA_AFISHA_FIRST.matcher(urlFromYandex).matches() ||
                    PATTERN_CINEMA_AFISHA_SECOND.matcher(urlFromYandex).matches()){
                int firstIndex = urlFromYandex.indexOf("http");
                urlFromYandex = urlFromYandex.substring(firstIndex);
                break;
            }
        }
        logger.info("End of method createURLFromQueryWithYandex() at " + LocalDateTime.now() + " - with result: " + urlFromYandex);
        return urlFromYandex;
    }

    //TODO: необходима проверка на входной запрос, если что убирать транслит, знаки препинания и тд
    @Override
    public String createUrlFromQuery(String queryForUrl) throws IOException {
        logger.info("Start method createURLFromQuery() at " + LocalDateTime.now() + " - with queryForUrl: " + queryForUrl);
        String urlFromGoogle = null;
        String urlFromYandex = null;

        StringBuffer urlQueryForYandex = new StringBuffer();
        String[] wordsFromQueryYandex = queryForUrl.split(" ");
        urlQueryForYandex.append("https://yandex.ru/search/?lr=213&text=");

        for (String word : wordsFromQueryYandex) {
            urlQueryForYandex.append(word + "+");
        }
        urlQueryForYandex.append(HELPER_FOR_QUERY_AFISHA);

        urlFromYandex = createURLFromQueryWithYandex(urlQueryForYandex.toString());
        if (urlFromYandex == null) {
            StringBuffer urlQueryForGoogle = new StringBuffer();
            String[] wordsFromQueryGoogle = queryForUrl.split(" ");
            urlQueryForGoogle.append("https://www.google.com/search?q=");

            for (String word : wordsFromQueryGoogle) {
                urlQueryForGoogle.append(word + "+");
            }
            urlQueryForGoogle.append(HELPER_FOR_QUERY_AFISHA);

            urlFromGoogle = createURLFromQueryWithGoogle(urlQueryForGoogle.toString());
        }
        String returnUrl = urlFromYandex;
        if (returnUrl == null) {
            returnUrl = urlFromGoogle;
        }
        if (returnUrl == null) {
            logger.info("############# Method createUrlFromQuery() don't find url, try again. Counter= " + counterOfCreatingUrl);
            counterOfCreatingUrl++;
            if (counterOfCreatingUrl > 100) {
                return null;
            }
            createUrlFromQuery(queryForUrl);
        }
        return returnUrl;
    }

    @Override
    public String getFilmIdFromQuery(String query) {
        logger.info("Start method getFilmIdFromQuery() at " + LocalDateTime.now() + " - with query: " + query);
        String filmId = "0";
        if (query.contains("movie")) {
            int firstIndex = query.indexOf("movie/");
            filmId = query.substring(firstIndex+6);
        } else if (query.contains("schedule_cinema_product/")) {
            int firstIndex = query.indexOf("schedule_cinema_product/");
            filmId = query.substring(firstIndex+24);
        }

        if (filmId.contains("/")) {
            filmId = filmId.replaceAll("/", "");
        }
        logger.info("End of method getFilmIdFromQuery() at " + LocalDateTime.now() + " - with result: " + filmId);
        return filmId;
    }

    @Override
    public Cinema getCinemaFromElement(Element element) throws IOException {
        logger.info("Start method getCinemaFromElement() at " + LocalDateTime.now());
        Cinema cinema = new Cinema();
        String nameCinema;
        String addressCinema;
        String underground;
        StringBuffer urlAddressCinema = new StringBuffer("https://www.afisha.ru");
        nameCinema = element.getElementsByAttributeValue("class","unit__movie-name").text();
        underground = element.select("div.unit__movie-location").text();
        urlAddressCinema.append(element.getElementsByAttributeValue("class", "unit__movie-name__link").attr("href"));
        try {
            //TODO: Сделать однократный поиск URL для кинотеатра и потом брать из БД. Очень тормозит работу.
            //Добавить проверку на наличие в БД и если нету, идти в интернет, а так при старте делать выгрузку из БД в HashMap Имя кинотетра - объект кинотеатр
            // И возвращать сразу готовый объект
            //addressCinema = getAddressCinemaFromUrlOrDB(urlAddressCinema.toString());
            addressCinema = "TODO: update in the future";
        } catch (Exception ex) {
            System.out.println("Error: " + ex + ". Необходимо оптимизировать поиск адресов кинотеатров");
            addressCinema = urlAddressCinema.toString();
        }


        cinema.setCinemaName(nameCinema);
        cinema.setCinemaUnderground(underground);
        cinema.setCinemaAddress(addressCinema);
        cinema.setUrlToAfisha(urlAddressCinema.toString());
        return cinema;
    }

    @Override
    public List<Session> getSessionFromElement(Element element) throws IOException {
        logger.info("Start method getSessionFromElement() at " + LocalDateTime.now());
        List<Session> timetable = new ArrayList<>();
        Session session;
        String minPriceMovie;
        String movieTimeShow;
        String typeOfMovieMovie;
        Elements elementsTimetable = element.select("li.tooltip.timetable__item");
        for (Element elementTimetable : elementsTimetable) {
            minPriceMovie = elementTimetable.getElementsByAttributeValue("class", "timetable__item-price").text();
            movieTimeShow = elementTimetable.getElementsByAttributeValue("class", "timetable__item-time").text();
            typeOfMovieMovie = elementTimetable.getElementsByAttributeValue("class", "tooltip__body").text();
            session = new Session();
            session.setTimeOfShow(movieTimeShow);
            session.setTypeOfMovie(typeOfMovieMovie);
            session.setPrice(minPriceMovie);
            timetable.add(session);
        }
        return timetable;
    }

    //TODO: добавить кинотеатры в БД и сначала проверять в бД и если нету уже потом парсить страницу. Ускорит работу.
    //Так же можно метод переделать в метод получения информации о кинотеатре. Либо этот, либо getCinemaFromElement()
    private String getAddressCinemaFromUrlOrDB(String urlForAddress) throws IOException {
        logger.info("Start method getAddressCinemaFromUrlOrDB() at " + LocalDateTime.now());
        Document cinemaHTMLdoc = pliProxyServer.getHttpDocumentFromInternet(urlForAddress);
        String address = cinemaHTMLdoc.select("label.unit__col-value-label").text();
        return address.isEmpty() ? "" : address;
    }

    public static void main(String[] args) throws IOException {
        PlParserAfisha p = new PlParserAfisha();
        PliProxyServer pliProxyServer = new PlProxyServer();
        String urlFromYandex = "";
        Document doc = pliProxyServer.getHttpDocumentFromInternet("https://yandex.ru/search/?lr=213&text=человек+паук+вдали+от+дома+afisha.ru");
        Elements elements = doc.select("ul.serp-list.serp-list_left_yes");
        for (Element element : elements.select("li.serp-item").select("a.link.link_theme_outer.path__item.i-bem")) {
            urlFromYandex = element.getElementsByTag("a").attr("href");
            if (PATTERN_CINEMA_AFISHA_FIRST.matcher(urlFromYandex).matches() ||
                    PATTERN_CINEMA_AFISHA_SECOND.matcher(urlFromYandex).matches()){
                int firstIndex = urlFromYandex.indexOf("http");
                urlFromYandex = urlFromYandex.substring(firstIndex);
                break;
            }
        }
        System.out.println("#####################" + urlFromYandex);


    }

    private PliProxyServer pliProxyServer;

    @Inject
    public void setPliProxyServer(PliProxyServer pliProxyServer) {
        this.pliProxyServer = pliProxyServer;
    }
}
