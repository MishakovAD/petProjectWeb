package com.project.CinemaTickets.backend.ServerLogic.Geolocation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class GeolocationImpl implements Geolocation {
    private Logger logger = LoggerFactory.getLogger(GeolocationImpl.class);

    @Override
    public Map<String, Double> getCoordinatesFromAddress(String address) {
        logger.debug("Start getCoordinatesFromAddress() in GeolocationImpl.class");
        Map<String, Double> coordinateMap = new HashMap<>();
        String url = ("http://search.maps.sputnik.ru/search/addr?q=" + address)
                .replaceAll(" ", "%20");
        BufferedReader reader;
        StringBuilder responseDocument = new StringBuilder();
        try {
            URLConnection connection = new URL(url).openConnection();
            reader  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            String line;
            while ((line = reader.readLine()) != null) {
                responseDocument.append(line);
            }
            JSONObject jsonObject = new JSONObject(responseDocument.toString());
            JSONObject resultObj = (JSONObject) jsonObject.get("result");
            JSONArray addressObj = (JSONArray) resultObj.get("address");
            JSONObject featuresObj = (JSONObject) addressObj.get(0);
            JSONArray featuresArr = (JSONArray) featuresObj.get("features");
            JSONObject geometriesObj = (JSONObject) featuresArr.get(0);
            JSONObject geometriesArr = (JSONObject) geometriesObj.get("geometry");
            JSONArray coordinatesArr = (JSONArray) geometriesArr.get("geometries");
            JSONObject coordinatesObj = (JSONObject) coordinatesArr.get(0);
            JSONArray coordinates = (JSONArray) coordinatesObj.get("coordinates");
            double latitude = coordinates.getDouble(1); //широта -указывается первой
            double longitude = coordinates.getDouble(0); //долгота -указывается второй
            coordinateMap.put("longitude", longitude);
            coordinateMap.put("latitude", latitude);
        } catch (IOException e) {
            logger.error("ERORR in getCoordinatesFromAddress() in GeolocationImpl.class \n", e);
        }
        logger.debug("End of getCoordinatesFromAddress() in GeolocationImpl.class");
        return coordinateMap;
    }

    @Override
    public boolean isClosesCoordinates(double userLatitude, double userLongitude, double objectLatitude, double objectLongitude) {
        logger.info("Start isClosesCoordinates() in GeolocationImpl.class");

        logger.info("End of detectCity() in GeolocationImpl.class");
        return false;
    }

    @Override
    public String detectCity(double latitude, double longitude) {
        logger.info("Start detectCity() in GeolocationImpl.class from coordinates");
        String city = "";
        String url = ("http://whatsthere.maps.sputnik.ru/point?lat=" + latitude + "&lon=" + longitude + "&houses=false");
        BufferedReader reader;
        StringBuilder responseDocument = new StringBuilder();
        try {
            URLConnection connection = new URL(url).openConnection();
            reader  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            String line;
            while ((line = reader.readLine()) != null) {
                responseDocument.append(line);
            }
            JSONObject jsonObject = new JSONObject(responseDocument.toString());
            JSONObject resultObj = (JSONObject) jsonObject.get("result");
            JSONArray addressObj = (JSONArray) resultObj.get("address");
            JSONObject featuresObj = (JSONObject) addressObj.get(0);
            JSONArray featuresArr = (JSONArray) featuresObj.get("features");
            JSONObject geometriesObj = (JSONObject) featuresArr.get(0);
            JSONObject geometriesArr = (JSONObject) geometriesObj.get("properties");
            city = (String) geometriesArr.get("description");
            String area = (String) geometriesArr.get("title"); //район в городе. Вдруг понадобится.
        } catch (IOException e) {
            logger.error("ERORR in detectCity() in GeolocationImpl.class \n", e);
        }

        if (city.contains(",")) {
            city = substringCity(city);
        }
        logger.info("End of detectCity() in GeolocationImpl.class");
        return city;
    }

    @Override
    public String detectCity(String ip) {
        logger.info("Start detectCity() in GeolocationImpl.class from IP");
        String city = "";

        logger.info("End of detectCity() in GeolocationImpl.class");
        return null;
    }

    private String substringCity (String city) {
        String result = city.substring(city.indexOf(",") + 2);
        if (result.contains(",")) {
            result = substringCity(result);
        }
        return result;
    }

    public static void main(String[] args) {
        GeolocationImpl g = new GeolocationImpl();
        //g.getCoordinatesFromAddress("г. Сочи, пос. Адлер, ул. Ульянова, 58");
        g.detectCity(43.428802, 39.924095);
    }
}
