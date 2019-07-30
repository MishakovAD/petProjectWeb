package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogicFromInternet;
import com.project.CinemaTickets.backend.Utils.JSONUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сделать дополнительную проверку на запрос. возвращет ли он инужные данные или нет. Искать похожие запросы среди списка премьер
 * чтобы не было такого, что фильм: какая то часть при неуказании части, не искались ссылки. В данном случае нужно брать последнюю часть
 * //TODO: Желательно из парсеров вообще убрать получение документа и передавать туда исключительно уже полученный документ.
 */
@Controller
public class TimesheetController {
    private List<Cinema> cinemaList;

    private Logger logger = LoggerFactory.getLogger(TimesheetController.class);

    @GetMapping({"/cinema"})
    public String getTimesheetPage() {
        logger.info("Start method getTimesheetPage() at " + LocalDateTime.now());
        return "timesheet";
    }

    @RequestMapping("/timesheet_all_query")
    public void timesheetAllQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method timesheetAllQuery() at " + LocalDateTime.now());
        String allQuery = request.getParameter("allQuery").trim();
        String content = "allQuery: " + allQuery;
        System.out.println(content);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", allQuery);
        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(jsonObject);
        writer.flush();
        writer.close();
//        logger.info("End of method respTimesheet() at " + LocalDateTime.now() + " - with result.size()= " + cinemaList.size());
    }

    //TODO: сделать потокобезопасную HashMap для многих пользователей, где ключ - сессия, которую получаем из реквест, а значение - List<Cinema>
    @RequestMapping("/timesheetquery")
    public void respTimesheet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method respTimesheet() at " + LocalDateTime.now());
        String timesheetquery = request.getParameter("timesheetquery").trim();
        String content = "timesheetquery: " + timesheetquery;

//        cinemaList = pliUserLogic.getCinemaListWithMovie(timesheetquery);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", timesheetquery);
        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(jsonObject);
        writer.flush();
        writer.close();
//        logger.info("End of method respTimesheet() at " + LocalDateTime.now() + " - with result.size()= " + cinemaList.size());
    }

    @RequestMapping("/timesheetquery_time")
    public void respTimesheetTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method respTimesheetTime() at " + LocalDateTime.now());
        String timesheetquery_time = request.getParameter("timesheetquery").trim();
        String content = "timesheetquery_time: " + timesheetquery_time;

//        List<Cinema> secondIterationCinemaList = pliUserLogic.updateCinemaListFromTimeShow(cinemaList, timesheetquery_time);
//        cinemaList = new ArrayList<>(secondIterationCinemaList);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", timesheetquery_time);
        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(jsonObject);
        writer.flush();
        writer.close();
//        logger.info("End of method respTimesheetTime() at " + LocalDateTime.now() + " - with result.size()= " + secondIterationCinemaList.size());
    }


    @RequestMapping("/timesheetquery_type")
    public void respTimesheetType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method respTimesheetType() at " + LocalDateTime.now());
        String timesheetquery_type = request.getParameter("timesheetquery").trim();

        String content = "timesheetquery_type: " + timesheetquery_type;

//        List<Cinema> thirdIterationCinemaList = pliUserLogic.updateCinemaListFromTypeShow(cinemaList, timesheetquery_type);
//        cinemaList = new ArrayList<>(thirdIterationCinemaList);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", timesheetquery_type);
        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(jsonObject);
        writer.flush();
        writer.close();
//        logger.info("End of method respTimesheetType() at " + LocalDateTime.now() + " - with result.size()= " + thirdIterationCinemaList.size());
    }

    @RequestMapping("/timesheetquery_place")
    public void respTimesheetPlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method respTimesheetPlace() at " + LocalDateTime.now());
        String timesheetquery_place = request.getParameter("timesheetquery").trim();

        String content = "timesheetquery_type: " + timesheetquery_place;

//        List<Cinema> foursIterationCinemaList = pliUserLogic.updateCinemaListFromPlace(cinemaList, timesheetquery_place);
//        cinemaList = new ArrayList<>(foursIterationCinemaList);


        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));

        JSONArray jsonArray = new JSONArray();
//        cinemaList.stream().forEach((cinema) -> {
//            cinema.getMovieList().stream().forEach((movie) -> {
//                try {
//                    movie.getSession().setUrl(pliParserKinopoisk.getUrlForBuyTicketsFromInternet(cinema, movie));
//                    Thread.sleep(5000);
//                    JSONObject jsonObject = JSONUtils.parseCinemaToJSON(cinema);
//                    jsonArray.put(jsonObject);
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        });
        writer.print(jsonArray);
        writer.flush();
        writer.close();
//        logger.info("End of method respTimesheetPlace() at " + LocalDateTime.now() + " - with result.size()= " + foursIterationCinemaList.size());

    }

    @RequestMapping("/getCurrentPosition")
    public void getCurrentPosition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getCurrentPosition() at " + LocalDateTime.now());
        String position = request.getParameter("getCurrentPosition");


        logger.info("End of method getCurrentPosition() at " + LocalDateTime.now() + " - with result= " + position);

    }

    @RequestMapping("/getUserIP")
    public void getUserIp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getUserIp() at " + LocalDateTime.now());
        String ip = request.getParameter("getUserIP");


        logger.info("End of method getUserIp() at " + LocalDateTime.now() + " - with ip= " + ip);

    }

    @RequestMapping("/getUserCityFromCite")
    public void getUserCityFromCite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getUserCityFromCite() at " + LocalDateTime.now());
        String city = request.getParameter("getUserCityFromCite");


        logger.info("End of method getUserCityFromCite() at " + LocalDateTime.now() + " - with city= " + city);

    }

    private PliUserLogicFromInternet pliUserLogic;
    private PliParserKinopoisk pliParserKinopoisk;

    @Inject
    private void setPliParserKinopoisk(PliParserKinopoisk pliParserKinopoisk) {
        this.pliParserKinopoisk = pliParserKinopoisk;
    }

    @Inject
    private void setPliUserLogic(PliUserLogicFromInternet pliUserLogic) {
        this.pliUserLogic = pliUserLogic;
    }
}
