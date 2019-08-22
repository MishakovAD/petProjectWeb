package com.project.CinemaTickets.backend.ServerLogic.UpdaterResults;

import com.project.CinemaTickets.backend.ServerLogic.DAO.DAOServerLogic;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Session;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.project.CinemaTickets.backend.constants.Constants.PATTERN_TIME;

@Component
public class UpdaterResultImpl implements UpdaterResult {
    private Logger logger = LoggerFactory.getLogger(UpdaterResultImpl.class);

    @Override
    public List<Session> updateFromPrice(List<Session> sessionList, String price) {
        logger.debug("Start updateFromPrice() in UpdaterResultImpl.class");
        List<Session> resultList = sessionList;
        if (price != null) {
            int priceInt = Integer.parseInt(price.replaceAll("\\D", ""));
            resultList = sessionList.stream().filter( session -> Integer.parseInt(session.getPrice().replaceAll("\\D", "")) <= priceInt).collect(Collectors.toList());
            logger.debug("End of updateFromPrice() in UpdaterResultImpl.class");
        }
        return resultList;
    }

    @Override
    public List<Session> updateFromType(List<Session> sessionList, String type) {
        logger.debug("Start updateFromType() in UpdaterResultImpl.class");
        List<Session> resultList;
        if (StringUtils.contains(type, "2")) {
            resultList = sessionList.stream().filter( session -> StringUtils.equalsAnyIgnoreCase(session.getTypeOfShow(), "2D")).collect(Collectors.toList());
        } else if (StringUtils.containsIgnoreCase(type, "IMAX")) {
            resultList = sessionList.stream().filter( session -> StringUtils.equalsAnyIgnoreCase(session.getTypeOfShow(), "IMAX")).collect(Collectors.toList());
        } else if (StringUtils.contains(type, "3")) {
            resultList = sessionList.stream().filter( session -> StringUtils.equalsAnyIgnoreCase(session.getTypeOfShow(), "3D")).collect(Collectors.toList());
        } else {
            resultList = sessionList;
        }
        logger.debug("End of updateFromType() in UpdaterResultImpl.class");
        return resultList;
    }

    @Override
    public List<Session> updateFromTime(List<Session> sessionList, String time) {
        logger.debug("Start updateFromTime() in UpdaterResultImpl.class");
        List<Session> resultList;
        resultList = sessionList.stream().filter( session -> isRrquiredPeriod(time, session.getTimeOfShow())).collect(Collectors.toList());
        logger.debug("End of updateFromTime() in UpdaterResultImpl.class");
        return resultList;
    }

    @Override
    public Map<Session, Cinema> updateFromPlace(List<Session> sessionList, String place) {
        logger.debug("Start updateFromPlace() in UpdaterResultImpl.class");
        Map<Session, Cinema> resultMap = new HashMap<>();
        Map<Session, Cinema> sessionCinemaMap = daoServerLogic.getCinemaWithSessions(sessionList);

        Iterator iterator = sessionCinemaMap.entrySet().iterator();
        if (place != null && !place.isEmpty() && !place.equals("")) {
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                Cinema cinema = (Cinema) pair.getValue();
                Session session = (Session) pair.getKey();
                if (StringUtils.containsIgnoreCase(cinema.getCinemaAddress(), place)) {
                    resultMap.put(session, cinema);
                } else if (StringUtils.containsIgnoreCase(cinema.getCinemaUnderground(), place)) {
                    resultMap.put(session, cinema);
                }
            }
        } else {
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                Cinema cinema = (Cinema) pair.getValue();
                Session session = (Session) pair.getKey();
                resultMap.put(session, cinema);
            }
        }

        logger.debug("End of updateFromPlace() in UpdaterResultImpl.class");
        return resultMap;
    }

    @Override
    public Map<Session, Cinema> updateFromPlace(List<Session> sessionList, double userLatitude, double userLongitude) {
        logger.debug("Start updateFromPlace() in UpdaterResultImpl.class");
        Map<Session, Cinema> resultMap = new HashMap<>();
        Map<Session, Cinema> sessionCinemaMap = daoServerLogic.getCinemaWithSessions(sessionList);

        logger.debug("End of updateFromPlace() in UpdaterResultImpl.class");
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
        logger.debug("Start method isRrquiredPeriod() in UpdaterResultImpl.class at " + LocalDateTime.now());
        //TODO: сделать возмость "сейчас", чтобы можно было купить билет на ближайший сеанс.
        //TODO: Так же необходимо сделать проверку на время 00.00, 00 00, 00-00 и другие возможные варианты
        // и если что, подгонять под них именно в этом меоде. чтобы не заюотиться об этом раньше
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


    //-----------------------Injections-----------------------------//
    private DAOServerLogic daoServerLogic;

    @Inject
    public void setDaoServerLogic (DAOServerLogic daoServerLogic) {
        this.daoServerLogic = daoServerLogic;
    }

    public static void main(String[] args) {
        UpdaterResultImpl u = new UpdaterResultImpl();
        System.out.println(u.isRrquiredPeriod("20:18", "21:20"));
    }
}
