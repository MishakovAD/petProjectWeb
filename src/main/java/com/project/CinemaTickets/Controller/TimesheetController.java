package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.backend.Parser.PliParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Сделать дополнительную проверку на запрос. возвращет ли он инужные данные или нет. Искать похожие запросы среди списка премьер
 * чтобы не было такого, что фильм: какая то часть при неуказании части, не искались ссылки. В данном случае нужно брать последнюю часть
 */
@Controller
public class TimesheetController {
    private int counterOfUseParser = 0;

    @GetMapping({"/cinema"})
    public String getTimesheetPage() {
        return "timesheet";
    }

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
     * Метод, который позволяет распарсить HTML страницу и получить из нее список всех кинотеатров, сеансов, адресов, стоимостью, рейтингом
     * для перемещения по страницам (а не "загрузить больше") использовать ссылку вида: https://www.afisha.ru/msk/schedule_cinema_product/232355/page2/?utm_content=afishapv
     * где меняется только параметр страницы
     * и проверять все страницы до тех пор, пока не придет ответ с 404 ошибкой.
     * @param query
     * @throws IOException
     */
    public void parser(String query) throws IOException {
        counterOfUseParser++;
        String url = plParser.createUrlFromQuery(query);
        String filmId = plParser.getFilmIdFromQuery(url);

        if (filmId.equals("0")) {
            parser(query);
        } else {
            System.out.println("До получения корректной ссылки parser() вызвался: " + counterOfUseParser + " раз");
            counterOfUseParser = 0;

            int pageCounter = plParser.counterOfPage(filmId);
            for (int i = 1; i <= pageCounter; i++) {
                StringBuffer urlStr = new StringBuffer();
                //TODO: At this time, this work only for Moscow. At future change "msk" on other country.
                urlStr.append("https://www.afisha.ru/msk/schedule_cinema_product/")
                        .append(filmId)
                        .append("/page")
                        .append(i);

                Document HTMLdoc = Jsoup.connect(urlStr.toString())
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
                plParser.parse(HTMLdoc);
                System.out.println("Parse " + i + " page was sucsessfull");
            }
        }
    }

    @Inject
    PliParser plParser;

    private void setPlParser (PliParser plParser) {
        this.plParser = plParser;
    }
}
