package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.CinemaEntity.Session;
import com.project.CinemaTickets.CinemaEntity.Timetable;
import com.project.CinemaTickets.backend.UserLogic.PlUserLogicFromInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlParserAfisha implements PliParser {
    @Override
    public List<Cinema> parse(Document HTMLdoc) throws IOException {
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
                    movie = new Movie(nameMovie, ratingMovie, cinema, session, dateFromMovie);
                    movieTimetableList.add(movie);
                }
                cinema.setMovieList(movieTimetableList);
                movieTimetableList = new ArrayList<>();
                cinemaList.add(cinema);
            }
        }


//        for (Cinema cinemaL : cinemaList) {
//            System.out.println("В кинотеатре " + cinemaL.getName() + " можно увидеть следующие фильмы: ");
//            for (Movie mov : cinemaL.getMovieList()) {
//                System.out.println(mov.toString());
//            }
//        }
        return cinemaList;
    }

    @Override
    public int counterOfPage(String filmId) throws IOException {
        int counterOfPage = 0;
        StringBuffer urlStr = new StringBuffer();
        //TODO: At this time, this work only for Moscow. At future change "msk" on other country.
        urlStr.append("https://www.afisha.ru/msk/schedule_cinema_product/")
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
        return counterOfPage;
    }

    //TODO: Сделать отдельную проверку на наличие определенных фраз(купить билет). В идеале это будет на нейронной сети.
    //TODO: А так же необходимо для обоих методов сделать проверку на полученную ссылку согласно паттерну, чтобы не было левых ссылок
    @Override
    public String createURLFromQueryWithGoogle(String query) throws IOException {
        String urlFromGoogle = null;
        StringBuffer urlQueryForGoogle = new StringBuffer();
        String[] wordsFromQuery = query.split(" ");
        urlQueryForGoogle.append("https://www.google.com/search?q=");

        for (String word : wordsFromQuery) {
            urlQueryForGoogle.append(word + "+");
        }
        urlQueryForGoogle.append("afisha.ru");

        Document googleHTMLdoc = Jsoup.connect(urlQueryForGoogle.toString())
                .userAgent("Mozilla/5.0 Chrome/75.0.3770.100 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
        Elements elements = googleHTMLdoc.select("div#main");
        for (Element element : elements.select("div.ZINbbc.xpd.O9g5cc.uUPGi")) {
            urlFromGoogle = element.getElementsByTag("a").attr("href");
            if (urlFromGoogle.contains("https://www.afisha.ru/movie/")){
                //System.out.println(urlByTagFromGoogle);
                int firstIndex = urlFromGoogle.indexOf("http");
                int lastIndex = urlFromGoogle.indexOf("/&");
                urlFromGoogle = urlFromGoogle.substring(firstIndex, lastIndex+1);
                break;
            }
        }

        return urlFromGoogle;
    }

    @Override
    public String createURLFromQueryWithYandex(String query) throws IOException {
        String urlFromYandex = null;
        StringBuffer urlQueryForYandex = new StringBuffer();
        String[] wordsFromQuery = query.split(" ");
        urlQueryForYandex.append("https://yandex.ru/search/?lr=213&text=");

        for (String word : wordsFromQuery) {
            urlQueryForYandex.append(word + "+");
        }
        urlQueryForYandex.append("afisha.ru");

        Document googleHTMLdoc = Jsoup.connect(urlQueryForYandex.toString())
                .userAgent("Mozilla/5.0 Chrome/75.0.3770.100 Safari/537.36")
                .referrer("http://www.yandex.ru")
                .get();
        Elements elements = googleHTMLdoc.select("ul.serp-list.serp-list_left_yes");
        for (Element element : elements.select("li.serp-item").select("a.link.link_theme_outer.path__item.i-bem")) {
            urlFromYandex = element.getElementsByTag("a").attr("href");
            if (urlFromYandex.contains("https://www.afisha.ru/") &&
            urlFromYandex.contains("schedule_cinema_product")){
                //System.out.println(urlByTagFromYandex);
                int firstIndex = urlFromYandex.indexOf("http");
                urlFromYandex = urlFromYandex.substring(firstIndex);
                break;
            }
        }

        return urlFromYandex;
    }

    //TODO: Тут добавить проверку на корректность ссылки. Минимум https://www.аfиша.ru/movie/filmId/, ну или хотя бы просто афиша
    @Override
    public String createUrlFromQuery(String queryForUrl) throws IOException {
        String urlFromGoogle = null;
        String urlFromYandex = null;
        urlFromYandex = createURLFromQueryWithYandex(queryForUrl);
        if (urlFromYandex == null) {
            urlFromGoogle = createURLFromQueryWithGoogle(queryForUrl);
        }
        return urlFromYandex != null ? urlFromYandex : urlFromGoogle;
    }

    @Override
    public String getFilmIdFromQuery(String query) {
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
        return filmId;
    }

    @Override
    public Cinema getCinemaFromElement(Element element) throws IOException {
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


        cinema.setName(nameCinema);
        cinema.setUnderground(underground);
        cinema.setAddress(addressCinema);
        cinema.setUrlToAfisha(urlAddressCinema.toString());
        return cinema;
    }

    @Override
    public List<Session> getSessionFromElement(Element element) throws IOException {
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
            session = new Session(movieTimeShow, typeOfMovieMovie, minPriceMovie);
            timetable.add(session);
        }
        return timetable;
    }

    //TODO: добавить кинотеатры в БД и сначала проверять в бД и если нету уже потом парсить страницу. Ускорит работу.
    //Так же можно метод переделать в метод получения информации о кинотеатре. Либо этот, либо getCinemaFromElement()
    private String getAddressCinemaFromUrlOrDB(String urlForAddress) throws IOException {
        Document googleHTMLdoc = Jsoup.connect(urlForAddress)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.yandex.ru")
                .get();
        String address = googleHTMLdoc.select("label.unit__col-value-label").text();
        return address.isEmpty() ? "" : address;
    }

    public static void main(String[] args) throws IOException {
        PlParserAfisha p = new PlParserAfisha();
        List<Cinema> cinemaListTest = new ArrayList<>();

        String urlWithFiltFromAfisha = p.createUrlFromQuery("Человек паук вдали от дома");
        String filmIdFromAfisha = p.getFilmIdFromQuery(urlWithFiltFromAfisha);


            int pageCounter = p.counterOfPage(filmIdFromAfisha);
            for (int i = 1; i <= pageCounter; i++) {
                StringBuffer urlStr = new StringBuffer();
                //TODO: At this time, this work only for Moscow. At future change "msk" on other country. Can search to IP address
                urlStr.append("https://www.afisha.ru/msk/schedule_cinema_product/")
                        .append(filmIdFromAfisha)
                        .append("/page")
                        .append(i);

                Document HTMLdoc = Jsoup.connect(urlStr.toString())
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
                cinemaListTest.addAll(p.parse(HTMLdoc));
            }

        PlUserLogicFromInternet plUserLogicFromInternet = new PlUserLogicFromInternet();
        List<Cinema> cl_time = plUserLogicFromInternet.updateCinemaListFromTimeShow(cinemaListTest, "22:30");
        List<Cinema> cl_type = plUserLogicFromInternet.updateCinemaListFromTypeShow(cl_time, "2D");

        for (Cinema cinemaL : cl_type) {
            System.out.println("В кинотеатре " + cinemaL.getName() + " можно увидеть следующие фильмы: ");
            for (Movie mov : cinemaL.getMovieList()) {
                System.out.println(mov.toString());
            }
        }

    }
}
