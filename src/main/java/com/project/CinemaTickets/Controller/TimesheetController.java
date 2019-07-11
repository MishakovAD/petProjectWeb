package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.backend.Parser.PliParser;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Сделать дополнительную проверку на запрос. возвращет ли он инужные данные или нет. Искать похожие запросы среди списка премьер
 * чтобы не было такого, что фильм: какая то часть при неуказании части, не искались ссылки. В данном случае нужно брать последнюю часть
 */
@Controller
public class TimesheetController {
    private int counterOfUseParser = 0;
    private List<Cinema> cinemaList;

    @GetMapping({"/cinema"})
    public String getTimesheetPage() {
        return "timesheet";
    }

    //TODO: сделать потокобезопасную HashMap для многих пользователей, где ключ - сессия, которую получаем из реквест, а значение - List<Cinema>
    @RequestMapping("/timesheetquery")
    public void respTimesheet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String timesheetquery = request.getParameter("timesheetquery").trim();

        String content = "timesheetquery: " + timesheetquery;

        parser(timesheetquery);
        response.setContentType("text/plain");

        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }

    /**
     * Метод, который позволяет распарсить HTML страницу
     * и получить из нее список всех кинотеатров, сеансов, адресов, стоимостью, рейтингом
     * для перемещения по страницам (а не "загрузить больше")
     * использовать ссылку вида: https://www.afisha.ru/msk/schedule_cinema_product/232355/page2/?utm_content=afishapv
     * где меняется только параметр страницы
     * и проверять все страницы до тех пор, пока не придет ответ с 404 ошибкой.
     * @param query
     * @throws IOException
     */
    public void parser(String query) throws IOException {
        cinemaList = new ArrayList<>();
        counterOfUseParser++;
        String url = pliParserAfisha.createUrlFromQuery(query);
        String filmId = pliParserAfisha.getFilmIdFromQuery(url);

        if (filmId.equals("0")) {
            parser(query);
        } else {
            System.out.println("До получения корректной ссылки parser() вызвался: " + counterOfUseParser + " раз");
            counterOfUseParser = 0;

            int pageCounter = pliParserAfisha.counterOfPage(filmId);
            for (int i = 1; i <= pageCounter; i++) {
                StringBuffer urlStr = new StringBuffer();
                //TODO: At this time, this work only for Moscow. At future change "msk" on other country. Can search to IP address
                urlStr.append("https://www.afisha.ru/msk/schedule_cinema_product/")
                        .append(filmId)
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
            pliParserKinopoisk.getUrlForBuyTickets(cinemaList.get(0), cinemaList.get(0).getMovieList().get(0));
        }
    }


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
