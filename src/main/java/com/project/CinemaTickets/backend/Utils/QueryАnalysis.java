package com.project.CinemaTickets.backend.Utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class QueryАnalysis {
    private static Logger logger = LoggerFactory.getLogger(QueryАnalysis.class);
    private static int counterForVreakRecursion = 0;

    public static Pattern PATTERN_IP_ADDRESS = Pattern.compile("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}");

    public static Map<String, String> parseQuerye (String query) {
        logger.debug("Start method parseQuerye() at " + LocalDateTime.now());
        Map<String, String> queryesMap = new HashMap<>();
        Arrays.stream(query.split("}")).forEach( queryPart -> {
            if (queryPart.contains("lat")) {
                for (String atr : queryPart.split(", ")) {
                    String key = atr.split(":")[0].trim();
                    String value = atr.replaceAll(key + " : ", "");
                    if (key.equals("lat")) {
                        queryesMap.put("latitude", value);
                    } else if (key.equals("lng")) {
                        queryesMap.put("longitude", value);
                    }
                }
            } else if (queryPart.contains("user_ip")) {
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                if (PATTERN_IP_ADDRESS.matcher(value).matches()) {
                    queryesMap.put(key, value);
                } else {
                    queryesMap.put(key, "0");
                }
            } else if (queryPart.contains("user_city")) {
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                queryesMap.put(key, value);
            } else if (queryPart.contains("movie")) {
                //TODO: возможно стоит сделать trim, проверить
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                String movieName = formatMovieName(value);
                queryesMap.put(key, movieName);
            } else if (queryPart.contains("time")) {
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                String time = formatTime(value);
                queryesMap.put(key, time);
            } else if (queryPart.contains("type")) {
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                String type = formatType(value);
                queryesMap.put(key, type);
            } else if (queryPart.contains("place")) {
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                String place = formatPlace(value);
                queryesMap.put(key, place);
            }
        });
        logger.debug("End of method parseQuerye() at " + LocalDateTime.now());
        return queryesMap;
    }

    public static String createUniqueID() {
        return UUID.randomUUID().toString()
                .substring(0, 13).replaceAll("-", String.valueOf(new Random(System.currentTimeMillis()).nextInt(777)));
    }

    private static String formatCity (String city) {
        return null;
    }

    private static String formatMovieName (String value) {
        //TODO: добавить проверку на случайный английский текст и перевод на русский.
        // Лучше сделать на стороне клиента, или же вообще просто предоставить выбор из всех возможных, а не заставлять вводить как угодно
        String movieName;
        boolean russianText = isRussiaText(value);
        if (russianText) {
            movieName = value;
        } else {
            movieName = "Текст введен на английском, повторите попытку";
        }
        return movieName;
    }

    private static boolean isRussiaText(String text) {
        int counterOfRussianChars = 0;
        char[] charArr = text.toCharArray();
        for (char c : charArr) {
            String chars = c + "";
            if (chars.matches("[а-яА-Я]")) {
                counterOfRussianChars++;
            }
        }

        if (counterOfRussianChars > 3) {
            return true;
        }
        return false;
    }

    private static String formatDate (String date) {
        return null;
    }

    private static String formatTime (String time) {
        counterForVreakRecursion++;
        if (counterForVreakRecursion > 3) {
            time = time.substring(0, 4);
        }
        String resultTime = time.trim();
        if (resultTime.length() > 5) {
            resultTime = resultTime.replaceAll("\\D", "");
            resultTime = formatTime(resultTime);
        } else if (resultTime.length() == 3) {
            char[] timeArr = resultTime.toCharArray();
            resultTime = timeArr[0] + ":" + timeArr[1] + "" + timeArr[2];
        } else if (resultTime.length() == 4) {
            char[] timeArr = resultTime.toCharArray();
            resultTime = timeArr[0] + "" + timeArr[1] + ":" + timeArr[2] + "" + timeArr[3];
        }
        return resultTime;
    }

    private static String formatType (String type) {
        String resultType;
        if (StringUtils.containsIgnoreCase(type, "max")) {
            resultType = "IMax";
        } else if (StringUtils.containsIgnoreCase(type, "3")) {
            resultType = "3D";
        } else if (StringUtils.containsIgnoreCase(type, "2")) {
            resultType = "2D";
        } else if (StringUtils.containsIgnoreCase(type, "dolby")) {
            resultType = "Dolby";
        } else {
            resultType = "2D";
        }
        return resultType;
    }

    private static String formatPlace (String place) {
        //TODO: придумать, как можно форматировать место
        String resultPlase = place;
        return resultPlase;
    }

    private static String formatCoordinates (String coordinates) {
        return null;
    }

    public static void main(String[] args) {
//        Map<String, String> map = parseQuerye("lat : 55.6844903, lng : 37.5954383}user_ip:185.89.8.146}user_city:Москва}movie:54321}time:54321}type:54321}place:54321}");
//        map.forEach( (key, value) -> System.out.println(key + "-" + value) );
//        System.out.println(PATTERN_IP_ADDRESS.matcher("192.1388.2.2").matches());
//        System.out.println(formatTime("123456"));
//        System.out.println(isRussiaText("asdsfrefdsf"));
        System.out.println(UUID.randomUUID().toString().substring(0, 13).replaceAll("-", String.valueOf(new Random(System.currentTimeMillis()).nextInt(777))));
    }
}
