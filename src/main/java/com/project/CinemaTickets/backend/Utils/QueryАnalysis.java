package com.project.CinemaTickets.backend.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class QueryАnalysis {
    private static Logger logger = LoggerFactory.getLogger(QueryАnalysis.class);

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
                String key = queryPart.split(":")[0];
                String value = queryPart.replaceAll(key + ":", "");
                String movieName = formatMovieName(value);
                queryesMap.put(key, movieName);
            }
        });
        logger.debug("End of method parseQuerye() at " + LocalDateTime.now());
        return queryesMap;
    }

    private static String formatCity (String city) {
        return null;
    }

    private static String formatMovieName (String value) {
        //TODO: добавить проверку на случайный английский текст и перевод на русский
        String movieName = value;
        return movieName;
    }

    private static String formatDate (String date) {
        return null;
    }

    private static String formatTime (String time) {
        return null;
    }

    private static String formatType (String type) {
        return null;
    }

    private static String formatPlace (String place) {
        return null;
    }

    private static String formatCoordinates (String coordinates) {
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> map = parseQuerye("lat : 55.6844903, lng : 37.5954383}user_ip:185.89.8.146}user_city:Москва}movie:54321}time:54321}type:54321}place:54321}");
        map.forEach( (key, value) -> System.out.println(key + "-" + value) );
        System.out.println(PATTERN_IP_ADDRESS.matcher("192.1388.2.2").matches());
    }
}
