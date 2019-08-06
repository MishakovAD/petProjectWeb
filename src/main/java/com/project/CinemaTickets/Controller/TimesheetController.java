package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.ServerLogic.UpdaterResults.UpdaterResult;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogicFromDB;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogicFromInternet;
import com.project.CinemaTickets.backend.Utils.JSONUtils;
import com.project.CinemaTickets.backend.Utils.QueryАnalysis;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сделать дополнительную проверку на запрос. возвращет ли он инужные данные или нет. Искать похожие запросы среди списка премьер
 * чтобы не было такого, что фильм: какая то часть при неуказании части, не искались ссылки. В данном случае нужно брать последнюю часть
 * //TODO: Желательно из парсеров вообще убрать получение документа и передавать туда исключительно уже полученный документ.
 */
@Controller
public class TimesheetController {
    private List<Cinema> cinemaList;
    private List<Movie> movieList;
    private List<Session> sessionList;
    Map<Session, Cinema> sessionCinemaMap = new HashMap<>();

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

        Map<String, String> queryMapAfterAnalys = QueryАnalysis.parseQuerye(allQuery);
        queryMapAfterAnalys.forEach((key, value) -> System.out.println(key + "-" + value));

        String movieName = queryMapAfterAnalys.get("movie");
        String city = queryMapAfterAnalys.get("user_city");

        if (city != null && !city.isEmpty() && !city.equals("")) {
            sessionList = pliUserLogicFromDB.getSessionListForMovie(movieName, city);
            System.out.println("###################" + sessionList.size());
        }

        String time = queryMapAfterAnalys.get("time");
        if (time != null && !time.isEmpty() && !time.equals("")) {
            sessionList = updaterResult.updateFromTime(sessionList, time);
        }

        String type = queryMapAfterAnalys.get("type");
        if (type != null && !type.isEmpty() && !type.equals("")) {
            sessionList = updaterResult.updateFromType(sessionList, type);
        }
        System.out.println("###################" + sessionList.size());

        String place = queryMapAfterAnalys.get("place");
        if (place != null && !place.isEmpty() && !place.equals("")) {
            sessionCinemaMap = updaterResult.updateFromPlace(sessionList, place);
        }

        double latitude = Double.parseDouble(queryMapAfterAnalys.get("latitude"));
        double longitude = Double.parseDouble(queryMapAfterAnalys.get("longitude"));

        JSONObject responseObject;
        JSONArray responseArray = new JSONArray();

        for (Map.Entry entrySet : sessionCinemaMap.entrySet()) {
            responseObject = new JSONObject();
            Session session = (Session) entrySet.getKey();
            Cinema cinema = (Cinema) entrySet.getValue();
            responseObject.put("cinemaName", cinema.getCinemaName());
            responseObject.put("cinemaAddress", cinema.getCinemaAddress());
            responseObject.put("cinemaUnderground", cinema.getCinemaUnderground());
            responseObject.put("cinemaUrl", cinema.getUrlToKinopoisk());
            responseObject.put("sessionType", session.getTypeOfShow());
            responseObject.put("sessionTime", session.getTimeOfShow());
            responseObject.put("sessionPrice", session.getPrice());
            responseObject.put("sessionDate", session.getSessionDate());
            responseObject.put("sesssionUrl", session.getUrl());
            responseObject.put("sesssionParent", session.getParent());
            responseArray.put(responseObject);
        }

        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(responseArray);
        writer.flush();
        writer.close();
        logger.info("End of method respTimesheet() at " + LocalDateTime.now() + " - with sessionCinemaMap.size()= " + sessionCinemaMap.size());
    }

    @RequestMapping("/get_available_movie")
    public void getAvailableMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getAvailableMovie() at " + LocalDateTime.now());
        String city = request.getParameter("user_city");

        JSONObject obj = new JSONObject();
        obj.put("movies", "доступные фильмы,Форсаж,Человек паук"); //перечесление без пробелов для корректного разделения.

        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(obj);
        writer.flush();
        writer.close();
        logger.info("End of method getAvailableMovie() at " + LocalDateTime.now());
    }

    @RequestMapping("/timesheet_autocomplete_movie")
    public void autocompleteMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getAvailableMovie() at " + LocalDateTime.now());
        String city = request.getParameter("user_city");

        JSONObject obj = new JSONObject();
        obj.put("movies", "доступные фильмы,Форсаж,Человек паук"); //перечесление без пробелов для корректного разделения.

        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print(obj);
        writer.flush();
        writer.close();
        logger.info("End of method getAvailableMovie() at " + LocalDateTime.now());
    }

    @RequestMapping("/get_cinema_in_city")
    public void getCinemaInUserCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getCinemaInUserCity() at " + LocalDateTime.now());
        String city = request.getParameter("user_city");

        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print("доступные кинотеатры");
        writer.flush();
        writer.close();
        logger.info("End of method getCinemaInUserCity() at " + LocalDateTime.now());
    }


    //--------------------------------- Injections -------------------------------------//
    private PliUserLogicFromInternet pliUserLogicFromInternet;
    private PliParserKinopoisk pliParserKinopoisk;
    private PliUserLogicFromDB pliUserLogicFromDB;
    private UpdaterResult updaterResult;

    @Inject
    public void setPliParserKinopoisk(PliParserKinopoisk pliParserKinopoisk) {
        this.pliParserKinopoisk = pliParserKinopoisk;
    }

    @Inject
    public void setPliUserLogic(PliUserLogicFromInternet pliUserLogicFromInternet) {
        this.pliUserLogicFromInternet = pliUserLogicFromInternet;
    }

    @Inject
    public void setPliUserLogicFromDB(PliUserLogicFromDB pliUserLogicFromDB) {
        this.pliUserLogicFromDB = pliUserLogicFromDB;
    }

    @Inject
    public void setUpdaterResult(UpdaterResult updaterResult) {
        this.updaterResult = updaterResult;
    }
}
