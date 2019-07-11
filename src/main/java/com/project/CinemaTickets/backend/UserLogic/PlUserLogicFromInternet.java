package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.backend.Parser.PliParser;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//TODO: Все sout заменить на Logger
@Component
public class PlUserLogicFromInternet implements PliUserLogic {
    public static String[] TYPES_OF_SHOW_FILM = {"2D", "3D", "IMax", "Dolby Atmos"};
    public static Pattern PATTERN_CINEMA_KINOPOISK = Pattern.compile("(https://www.kinopoisk.ru/afisha/city/\\d+/cinema/[a-z0-9A-Zа-яА-Я -]+/?)");

    private int counterTryGetFilmId = 0;

    @Override
    public List<Cinema> getCinemaListWithMovie(String userQuery) throws IOException {
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

                Document HTMLdoc = Jsoup.connect(urlStr.toString())
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
                cinemaList.addAll(pliParserAfisha.parse(HTMLdoc));
                System.out.println("Parse " + i + " page was sucsessfull! " + "size= " + cinemaList.size());
            }
            /*
            Какая то логика, которая будет выбирать подходящий сеанс. Возможно вынести это в отдельный метод.
            Пока передам просто 1 элемент из листа
             */
            //pliParserKinopoisk.getUrlForBuyTickets(cinemaList.get(0), cinemaList.get(0).getMovieList().get(0));
        }
        return cinemaList;
    }

    @Override
    public List<Cinema> updateCinemaListFromTypeShow(List<Cinema> cinemaList, String type) {
        List<Cinema> cinemaListNew = new ArrayList<>();
        List<Movie> movieListOld;
        List<Movie> movieListNew; //Сюда добавляем только те фильмы, что удовлетворяют условию
        for (Cinema cinema : cinemaList) {
            movieListOld = cinema.getMovieList();
            for (Movie movie : movieListOld) {

            }
        }
        return null;
    }

    @Override
    public List<Cinema> updateCinemaListFromTimeShow(List<Cinema> cinemaList, String time) {
        return null;
    }

    @Override
    public List<Cinema> updateCinemaListFromPlace(List<Cinema> cinemaList, String place) {
        return null;
    }

    //--------------------------Injection part------------------------//

    PliParser pliParserAfisha;
    PliParserKinopoisk pliParserKinopoisk;

    @Inject
    private void setPlParser (PliParser pliParser) {
        this.pliParserAfisha = pliParser;
    }

    @Inject
    private void setPliParserKinopoisk (PliParserKinopoisk pliParserKinopoisk1) {
        this.pliParserKinopoisk = pliParserKinopoisk1;
    }
}
