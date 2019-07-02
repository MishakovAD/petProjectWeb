package com.project.CinemaTickets.backend.Parser;

import com.project.CinemaTickets.CinemaEntity.Movie;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class PlParser implements PliParser {
    @Override
    public void parse(Document HTMLdoc) {
        List<Movie> movieList = new ArrayList<>();
        Elements elements = HTMLdoc.select("div.content.content_view_list");

        for (Element element : elements) {
//            Elements el = element.children().addClass("a.unit__movie-name__link");
//            System.out.println(el.toString());
            System.out.println(element.toString());
        }
    }
}
