package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.CinemaEntity.Movie;
import com.project.CinemaTickets.CinemaEntity.Session;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.UserLogic.PlUserLogicFromInternet;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogic;
import com.project.CinemaTickets.backend.Utils.JSONUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
        if (request != null) {
            String timesheetquery = request.getParameter("timesheetquery").trim();
            String content = "timesheetquery: " + timesheetquery;

            cinemaList = pliUserLogic.getCinemaListWithMovie(timesheetquery);

//            for (Cinema cinemaL : cinemaList) {
//                System.out.println("В кинотеатре " + cinemaL.getName() + " можно увидеть следующие фильмы: ");
//                for (Movie mov : cinemaL.getMovieList()) {
//                    System.out.println(mov.toString());
//                }
//            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query", timesheetquery);
            response.setContentType("application/json");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            writer.print(jsonObject);
            writer.flush();
            writer.close();
        }
    }

    @RequestMapping("/timesheetquery_time")
    public void respTimesheetTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request != null) {
            String timesheetquery_time = request.getParameter("timesheetquery").trim();
            String content = "timesheetquery_time: " + timesheetquery_time;

            List<Cinema> secondIterationCinemaList = pliUserLogic.updateCinemaListFromTimeShow(cinemaList, timesheetquery_time);
            cinemaList = new ArrayList<>(secondIterationCinemaList);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query", timesheetquery_time);
            response.setContentType("application/json");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            writer.print(jsonObject);
            writer.flush();
            writer.close();

//            response.setContentType("text/plain");
//            response.setContentType("application/json");
//            OutputStream outStream = response.getOutputStream();
//            outStream.write(content.getBytes("UTF-8"));
//            outStream.flush();
//            outStream.close();
        }

    }



    @RequestMapping("/timesheetquery_type")
    public void respTimesheetType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request != null) {
            String timesheetquery_type = request.getParameter("timesheetquery").trim();

            String content = "timesheetquery_type: " + timesheetquery_type;

            List<Cinema> thirdIterationCinemaList = pliUserLogic.updateCinemaListFromTypeShow(cinemaList, timesheetquery_type);
            cinemaList = new ArrayList<>(thirdIterationCinemaList);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query", timesheetquery_type);
            response.setContentType("application/json");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            writer.print(jsonObject);
            writer.flush();
            writer.close();


//            response.setContentType("application/json");
//            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
//
//            JSONArray jsonArray = new JSONArray();
//            for (Cinema cinemaL : cinemaList) {
//                JSONObject jsonObject = JSONUtils.parseCinemaToJSON(cinemaL);
//                jsonArray.put(jsonObject);
//                //System.out.println("В кинотеатре " + cinemaL.getName() + " можно увидеть следующие фильмы: ");
//                for (Movie mov : cinemaL.getMovieList()) {
//                    //System.out.println(mov.toString());
//                }
//            }
//            writer.print(jsonArray);
//            writer.flush();
//            writer.close();

        }

    }

    @RequestMapping("/timesheetquery_place")
    public void respTimesheetPlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request != null) {
            String timesheetquery_place = request.getParameter("timesheetquery").trim();

            String content = "timesheetquery_type: " + timesheetquery_place;

            List<Cinema> foursIterationCinemaList = pliUserLogic.updateCinemaListFromPlace(cinemaList, timesheetquery_place);
            cinemaList = new ArrayList<>(foursIterationCinemaList);


            response.setContentType("application/json");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));

            JSONArray jsonArray = new JSONArray();
            System.out.println("Start search url");
            for (Cinema cinemaL : cinemaList) {
                for (Movie mov : cinemaL.getMovieList()) {
                    mov.getSession().setUrl(pliParserKinopoisk.getUrlForBuyTickets(cinemaL, mov));
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject jsonObject = JSONUtils.parseCinemaToJSON(cinemaL);
                jsonArray.put(jsonObject);
            }
            writer.print(jsonArray);
            writer.flush();
            writer.close();

        }

    }

    private PliUserLogic pliUserLogic;
    private PliParserKinopoisk pliParserKinopoisk;

    @Inject
    private void setPliParserKinopoisk (PliParserKinopoisk pliParserKinopoisk) {
        this.pliParserKinopoisk = pliParserKinopoisk;
    }

    @Inject
    private void setPliUserLogic (PliUserLogic pliUserLogic) {
        this.pliUserLogic = pliUserLogic;
    }
}
