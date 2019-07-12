package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.backend.Parser.PliParser;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import net.bytebuddy.asm.Advice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: Все sout заменить на Logger
@Component
public class PlUserLogicFromInternet implements PliUserLogic {
    public static String[] TYPES_OF_SHOW_FILM = {"2D", "3D", "IMax", "Dolby Atmos"};
    public static Pattern PATTERN_CINEMA_KINOPOISK = Pattern.compile("(https://www.kinopoisk.ru/afisha/city/\\d+/cinema/[a-z0-9A-Zа-яА-Я -]+/?)");
    public static Pattern PATTERN_TIME = Pattern.compile("(\\d{2}):(\\d{2})");

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
            if (movieListNew.size() == 0) {
                continue;
            } else {
                cinemaListNew.add(cinema);
            }
        }
        return cinemaListNew;
    }

    @Override
    public List<Cinema> updateCinemaListFromTimeShow(List<Cinema> cinemaList, String userTime) {
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
            if (movieListNew.size() == 0) {
                continue;
            } else {
                cinemaListNew.add(cinema);
            }
        }
        return cinemaListNew;
    }

    @Override
    public List<Cinema> updateCinemaListFromPlace(List<Cinema> cinemaList, String place) {
        return null;
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

    public static void main(String[] args) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate ld = LocalDate.now();
        LocalTime current = LocalTime.of(17, 13);
        LocalTime movie = LocalTime.of(15, 30);
        movie = movie.minusHours(current.getHour());

        PlUserLogicFromInternet p = new PlUserLogicFromInternet();

        System.out.println(p.isRrquiredPeriod("20:00", "19:50"));
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
