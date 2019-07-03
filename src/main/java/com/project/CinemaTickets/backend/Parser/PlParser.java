package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class PlParser implements PliParser {
    @Override
    public void parse(Document HTMLdoc) {
        List<Movie> movieList = new ArrayList<>();
        Elements elements = HTMLdoc.select("ul.listSchedule___11A9v");

        for (Element element : elements.select("li")) {
            Elements rootElements = element.getElementsByAttributeValue("class", "unit__schedule-row");
            for (Element rootElement : rootElements) {
                String cinema = rootElement.getElementsByAttributeValue("class","unit__movie-name").text();
                String session = rootElement.getElementsByAttributeValue("class", "timetable").text();
                System.out.println("Кинотеатр: " + cinema + " \nпредлагает следующие сеансы: " + session);
            }
            System.out.println("=============================================");
        }
    }

    @Override
    public int counterOfPage(String filmId) throws IOException {
        int counterOfPage = 0;
        StringBuffer urlStr = new StringBuffer();
        //TODO: At this time, this work only for Moscow. At future change "msk" on other country.
        urlStr.append("https://www.afisha.ru/msk/schedule_cinema_product/")
                .append(filmId)
                .append("/page");

        for (int i = 1; ; i++){
            StringBuffer correctUrl = new StringBuffer(urlStr);
            correctUrl.append(i);
            URL url = new URL(correctUrl.toString());
            URLConnection urlConnection = url.openConnection();
            if (urlConnection.getHeaderField(0).contains("404")) {
                counterOfPage = i - 1;
                break;
            } else if (i > 777) {
                break;
            }
        }
        return counterOfPage;
    }

    @Override
    public String createURLFromQuery(String query) throws IOException {
        StringBuffer urlStr = new StringBuffer();
        String[] wordsFromQuery = query.split(" ");
        urlStr.append("https://www.google.com/search?q=");
        for (String word : wordsFromQuery) {
            urlStr.append(word + "+");
        }

        Document googleHTMLdoc = Jsoup.connect(urlStr.toString())
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
        //Elements elements = googleHTMLdoc.select("body");
        Elements elements = googleHTMLdoc.select("span.BNeawe");
        for (Element element : elements.select("div.r")) {
            String url = element.attr("href");
            String urlByTag = element.getElementsByTag("a").attr("href");
            String urlByTagCite = element.getElementsByAttributeValue("cite", "iUh30").text();
            System.out.println(url);
            System.out.println(urlByTag);
            System.out.println(urlByTagCite);
            System.out.println("++++++++++++++++++++++++++++");
        }

        return null;
    }

    @Override
    public String getFilmIdFromQuery(String query) {
        return null;
    }

    public static void main(String[] args) throws IOException {
        PlParser p = new PlParser();
        p.createURLFromQuery("купить билеты аладин");
    }
}
