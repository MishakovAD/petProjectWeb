package com.project.CinemaTickets.backend.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class QueryАnalysis {
    private static Logger logger = LoggerFactory.getLogger(QueryАnalysis.class);

    public static Map<String, String> parseQuerye (String query) {
        logger.debug("Start method parseQuerye() at " + LocalDateTime.now());
        Map<String, String> queryesMap = new HashMap<>();
        for (String atr : query.split(", ")) {
            String key = atr.split(":")[0];
            String value = atr.replaceAll(key + ":", "");
            queryesMap.put(key, value);
        }
        logger.debug("End of method parseQuerye() at " + LocalDateTime.now());
        return queryesMap;
    }

    public static void main(String[] args) {
        Map<String, String> map = parseQuerye("movie:1234, time:1234:123, type:1234, place:1234");
        map.forEach( (key, value) -> System.out.println(key + "-" + value) );
    }
}
