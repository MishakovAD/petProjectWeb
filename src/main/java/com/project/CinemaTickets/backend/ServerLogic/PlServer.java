package com.project.CinemaTickets.backend.ServerLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ProxyServer.PliProxyServer;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlServer implements PliServer {

    private Logger logger = LoggerFactory.getLogger(PlServer.class);

    @Override
    public List<Cinema> getAllCinemasFromKinopoisk(String date) throws IOException {
        logger.info("Start method getAllCinemasFromKinopoisk() at " + LocalDateTime.now());
        List<Cinema> cinemasList = new ArrayList<>();
        ArrayList<String> idCinemasList = createIdCinemas(); //TODO:сделать в дальнешем умный апдейт данного листа. То ест ьудалять айдишники, кинотеатров которых нет.
        ArrayList<String> notValidIdList = new ArrayList<>();
        Random random = new Random();
        while (true) {
            int index = random.nextInt(idCinemasList.size() + 1);
            Document document = pliProxyServer.getHttpDocumentFromInternet("https://www.kinopoisk.ru/afisha/city/1/cinema/" + idCinemasList.get(index) + "/");

            if (document.text().contains("Файл не найден. Ошибка 404.")) {
                notValidIdList.add(idCinemasList.get(index));
                idCinemasList.remove(index);
                if (idCinemasList.size() == 0) {
                    break;
                }
            } else {
                idCinemasList.remove(index);
                if (idCinemasList.size() == 0) {
                    break;
                }

                Cinema cinema = pliParserKinopoisk.getCinemaFromDocument(document);
                cinemasList.add(cinema);

                emulationHumanActivity();
            }

        }
        return cinemasList;
    }

    @Override
    public Cinema getCinemaForDBFromKinopoisk(int cinemaId, String date) {
        return null;
    }

    @Override
    public void emulationHumanActivity() throws IOException {
        int counterCimulations = 0;
        List<String> cimulationQueries = createCimulationQueries();
        for (String query : cimulationQueries) {
            counterCimulations++;
            if (counterCimulations > 5) { //TODO: выбрать наиболее оптимальное число для симуляций
                break;
            }
            String urlFromSearch = pliProxyServer.createUrlFromQueryForProxyServer(query, true);
        }


    }

    private ArrayList<String> createIdCinemas() {
        ArrayList<String> idList = new ArrayList<>();
        for (int i = 280268; i < 281250; i++){
            idList.add(String.valueOf(i));
        }
        return idList;
    }

    private ArrayList<String> createCimulationQueries() {
        ArrayList<String> cimulationQueries = new ArrayList<>();
        ArrayList<String> cimulationQueriesFirstPart = new ArrayList<>();
        cimulationQueriesFirstPart.add("Купить");
        cimulationQueriesFirstPart.add("Смотреть онлайн");
        cimulationQueriesFirstPart.add("Что делать, если");
        cimulationQueriesFirstPart.add("болит горло");
        cimulationQueriesFirstPart.add("алиэкспресс");
        ArrayList<String> cimulationQueriesSecondPart = new ArrayList<>();
        cimulationQueriesSecondPart.add("дешевую дрель");
        cimulationQueriesSecondPart.add("классную стиральную машинку");
        cimulationQueriesSecondPart.add("BMW х5");
        cimulationQueriesSecondPart.add("переднеприводную машину");
        cimulationQueriesSecondPart.add("грузовик");
        cimulationQueriesSecondPart.add("билеты на самолет");
        cimulationQueriesSecondPart.add("айфон 6");
        cimulationQueriesSecondPart.add("квартиру в Москве дешево");
        cimulationQueriesSecondPart.add("сервер по выгодной цене");
        cimulationQueriesSecondPart.add("зимнюю резину для lada");
        cimulationQueriesSecondPart.add("диски");
        ArrayList<String> cimulationQueriesThirdPart = new ArrayList<>();
        cimulationQueriesThirdPart.add("сериал Люцифер на русском");
        cimulationQueriesThirdPart.add("погружение в хорошем качестве hd720");
        cimulationQueriesThirdPart.add("трансляцию футбола");
        cimulationQueriesThirdPart.add("курсы по java");
        cimulationQueriesThirdPart.add("стрим WoW");
        cimulationQueriesThirdPart.add("команда а");
        cimulationQueriesThirdPart.add("");
        cimulationQueriesThirdPart.add("россия 1");
        cimulationQueriesThirdPart.add("дом 2");
        ArrayList<String> cimulationQueriesFourPart = new ArrayList<>();
        cimulationQueriesFourPart.add("умер хомячок");
        cimulationQueriesFourPart.add("упал в колодец");
        cimulationQueriesFourPart.add("украл миллион");
        cimulationQueriesFourPart.add("получил приз");
        cimulationQueriesFourPart.add("выпала 13 черное");
        cimulationQueriesFourPart.add("прыгнул с обрыва");
        cimulationQueriesFourPart.add("сломал кота");
        ArrayList<String> cimulationQueriesFivesPart = new ArrayList<>();
        cimulationQueriesFivesPart.add("у кота");
        cimulationQueriesFivesPart.add("у ребенка");
        cimulationQueriesFivesPart.add("у маленькой черепашки");
        cimulationQueriesFivesPart.add("водопроводного крана");
        cimulationQueriesFivesPart.add("у собаки");
        cimulationQueriesFivesPart.add("у Леонида Николаевича");
        ArrayList<String> cimulationQueriesSixPart = new ArrayList<>();
        cimulationQueriesSixPart.add("купить китайские часы");
        cimulationQueriesSixPart.add("купить китайский браслет");
        cimulationQueriesSixPart.add("купить китайский айфон 5");
        cimulationQueriesSixPart.add("купить тонировку для стекол");
        cimulationQueriesSixPart.add("купить маленькую черепашку");
        cimulationQueriesSixPart.add("купить много полезного");
        cimulationQueriesSixPart.add("купить 5 лампочек");



        return cimulationQueries;
    }



//-----------------------Injections-----------------------------//
    private PliProxyServer pliProxyServer;
    private PliParserKinopoisk pliParserKinopoisk;

    @Inject
    public void setPliProxyServer(PliProxyServer pliProxyServer) {
        this.pliProxyServer = pliProxyServer;
    }
    @Inject
    public void setPliParserKinopoisk (PliParserKinopoisk pliParserKinopoisk1) {
        this.pliParserKinopoisk = pliParserKinopoisk1;
    }
}
