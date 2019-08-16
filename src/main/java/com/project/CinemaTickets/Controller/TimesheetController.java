package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Movie;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils.HibernateDao;
import com.project.CinemaTickets.backend.ServerLogic.UpdaterResults.UpdaterResult;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogicFromDB;
import com.project.CinemaTickets.backend.UserLogic.PliUserLogicFromInternet;
import com.project.CinemaTickets.backend.Utils.JSONUtils;
import com.project.CinemaTickets.backend.Utils.QueryАnalysis;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @PostConstruct
    public void init() {
        movieList = hibernateDao.selectAllMovie(); //TODO: Выборка movie относительно города (сравнивать по кинотеатрам)
        cinemaList = hibernateDao.selectAllCinema();
        //sessionList = hibernateDao.selectAllSession(); //Лишняя информация
    }

    @GetMapping({"/cinema"})
    public String getTimesheetPage() {
        logger.info("Start method getTimesheetPage() at " + LocalDateTime.now());
        return "timesheet";
    }

    @RequestMapping("/prepareSessionList")
    public void prepareSessionForMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method prepareSessionForMovie() at " + LocalDateTime.now());
        String movieName = request.getParameter("movieName");
        Movie movieObj = movieList.stream().filter(movie -> StringUtils.equalsIgnoreCase(movie.getMovieName(), movieName)).findAny().get();
        sessionList = hibernateDao.selectSessionsForMovie(movieObj);

        if (sessionList == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            writer.print("Для данного фильма сеансов не найдено");
            writer.flush();
            writer.close();
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Status Code", "200");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
            if (sessionList.size() == 0) {
                writer.print("Для данного фильма сеансов не найдено");
            } else {
                writer.print("Для данного фильма найдено сеансов: " + sessionList.size());
            }
            writer.flush();
            writer.close();
        }

        logger.info("End of method prepareSessionForMovie() at " + LocalDateTime.now() + " - with sessionList.size()= " + sessionList.size());
    }

    @RequestMapping("/prepareResult")
    public void prepareResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method timesheetAllQuery() at " + LocalDateTime.now());
        String query = request.getParameter("query").trim();

        Map<String, String> queryMapAfterAnalys = QueryАnalysis.parseQuerye(query);

        String time = queryMapAfterAnalys.get("time");
        if (time != null && !time.isEmpty() && !time.equals("")) {
            sessionList = updaterResult.updateFromTime(sessionList, time);
        }

        String type = queryMapAfterAnalys.get("type");
        if (type != null && !type.isEmpty() && !type.equals("")) {
            sessionList = updaterResult.updateFromType(sessionList, type);
        }

        String price = queryMapAfterAnalys.get("price");
        if (price != null && !price.isEmpty() && !price.equals("")) {
            sessionList = updaterResult.updateFromPrice(sessionList, price);
        }

        String place = queryMapAfterAnalys.get("place");
        if (place != null && !place.isEmpty() && !place.equals("")) {
            sessionCinemaMap = updaterResult.updateFromPlace(sessionList, place);
        }

        if (queryMapAfterAnalys.get("latitude") != null) {
            double latitude = Double.parseDouble(queryMapAfterAnalys.get("latitude"));
        }
        if (queryMapAfterAnalys.get("longitude") != null) {
            double longitude = Double.parseDouble(queryMapAfterAnalys.get("longitude"));
        }

        JSONArray responseArray = new JSONArray();
        Set<String> uniqueCinemaId = new HashSet<>();

        sessionList.forEach(session -> {
            if (!uniqueCinemaId.contains(session.getCinema_id())) {
                uniqueCinemaId.add(session.getCinema_id());
            }
        });

        Set<Session> resultSessionSet = new HashSet<>();
        sessionList.forEach(s -> {
            if (resultSessionSet.stream().filter(rs -> StringUtils.equals(rs.getSession_id(), s.getSession_id())).collect(Collectors.toList()).size() == 0) {
                resultSessionSet.add(s);
            }
        });

        uniqueCinemaId.forEach(cinemaId -> {
            JSONArray responseArrayByGroupCinema = new JSONArray();
            List<Session> sessionListFromCinema = resultSessionSet.stream().filter(s -> s.getCinema_id().equals(cinemaId)).collect(Collectors.toList());
            sessionListFromCinema.forEach(groupSession -> {
                JSONObject responseObject = new JSONObject();
                responseObject.put("movie", movieList.stream()
                        .filter(m -> m.getMovie_id().equals(groupSession.getMovie_id()))
                        .findFirst()
                        .get()
                        .getMovieName());
                responseObject.put("cinema", cinemaList.stream()
                        .filter(c -> c.getCinema_id().equals(groupSession.getCinema_id()))
                        .findFirst()
                        .get()
                        .getCinemaName());
                responseObject.put("sessionType", groupSession.getTypeOfShow());
                responseObject.put("sessionTime", groupSession.getTimeOfShow());
                responseObject.put("sessionPrice", groupSession.getPrice());
                responseObject.put("sessionDate", groupSession.getSessionDate());
                responseObject.put("sesssionUrl", groupSession.getUrl());
                responseArrayByGroupCinema.put(responseObject);
            });
            responseArray.put(responseArrayByGroupCinema);
        });

        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        if (sessionList.size() == 0) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        writer.print(responseArray);
        writer.flush();
        writer.close();
        logger.info("End of method respTimesheet() at " + LocalDateTime.now() + " - with sessionCinemaMap.size()= " + sessionCinemaMap.size());
    }

    @RequestMapping("/get_available_movie")
    public void getAvailableMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getAvailableMovie() at " + LocalDateTime.now());
        String city = request.getParameter("user_city");

        if (movieList == null || movieList.size() == 0) {
            movieList = pliUserLogicFromDB.getMovieListForUser();
        }
        StringBuilder movieNames = new StringBuilder();
        movieList.forEach( movie -> movieNames.append(movie.getMovieName()).append("}"));

        JSONObject obj = new JSONObject();
        obj.put("movies", movieNames.toString()); //перечесление без пробелов для корректного разделения.

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
    private HibernateDao hibernateDao;

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

    @Inject
    public void setHibernateDao (HibernateDao hibernateDao) {
        this.hibernateDao = hibernateDao;
    }
}
