package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.backend.Parser.PliParser;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogic;
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

        List<Cinema> firstIterationCinemaList = pliUserLogic.getCinemaListWithMovie(timesheetquery);

        response.setContentType("text/plain");
        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }


    private PliUserLogic pliUserLogic;

    @Inject
    private void setPliUserLogic (PliUserLogic pliUserLogic) {
        this.pliUserLogic = pliUserLogic;
    }
}
