package com.project.CinemaTickets.backend.ServerLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;
import com.project.CinemaTickets.backend.Parser.PlParserKinopoisk;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ProxyServer.PlProxyServer;
import com.project.CinemaTickets.backend.ProxyServer.PliProxyServer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.print.Doc;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        while (true) {
            Random random = new Random(System.currentTimeMillis());
            int index = random.nextInt(idCinemasList.size());
            Document document;

            if (date == null || date.isEmpty() || StringUtils.equals(date, "")) {
                document = pliProxyServer.getHttpDocumentFromInternet("https://www.kinopoisk.ru/afisha/city/1/cinema/" + idCinemasList.get(index) + "/");
            } else {
                document = pliProxyServer.getHttpDocumentFromInternet("https://www.kinopoisk.ru/afisha/city/1/cinema/" + idCinemasList.get(index)
                        + "/" + "day_view/" + LocalDate.parse(date).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/");
            }


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
        logger.info("End of method getAllCinemasFromKinopoisk() at " + LocalDateTime.now());
        return cinemasList;
    }

    @Override
    public Cinema getCinemaForDBFromKinopoisk(int cinemaId, String date) {
        logger.info("Start method getCinemaForDBFromKinopoisk() at " + LocalDateTime.now());
        return null;
    }

    @Override
    public void emulationHumanActivity() throws IOException {
        logger.info("Start method emulationHumanActivity() at " + LocalDateTime.now());
        if (PlProxyServer.proxyListFromInternet.size() == 0) {
            PlProxyServer.proxyListFromInternet.addAll(pliProxyServer.getProxyFromInternet(null));
        }
        int counterCimulations = 0;
        List<String> cimulationQueries = createCimulationQueries();
        for (int i = 0; i < cimulationQueries.size(); i++) {
            Random random = new Random(System.currentTimeMillis());
            int index = random.nextInt(cimulationQueries.size());
            String query = cimulationQueries.get(index);
            counterCimulations++;
            if (counterCimulations > 3) { //TODO: выбрать наиболее оптимальное число для симуляций
                break;
            }
            String urlFromSearch = pliProxyServer.createUrlFromQueryForProxyServer(query, true);

            timeout(random);

            if (urlFromSearch != null) {
                Document emulationDoc = pliProxyServer.getHttpDocumentFromInternet(urlFromSearch);
                String urlFromDoc = emulationDoc.getElementsByAttribute("href").first().attributes().getIgnoreCase("href");
                if (urlFromDoc != null) {
                    Document lastDoc = pliProxyServer.getHttpDocumentFromInternet(urlFromDoc);
                }
            }


            logger.info("####################### найденные url для эмуляции деятельности: " + urlFromSearch);
        }

        logger.info("Start method emulationHumanActivity() at " + LocalDateTime.now());
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

        for (String firstStr : cimulationQueriesFirstPart) {
            if (StringUtils.equalsAnyIgnoreCase(firstStr, "Купить")) {
                for (String secondStr : cimulationQueriesSecondPart) {
                    cimulationQueries.add(firstStr + " " + secondStr);
                }
            }
            if (StringUtils.equalsAnyIgnoreCase(firstStr, "Смотреть онлайн")) {
                for (String secondStr : cimulationQueriesThirdPart) {
                    cimulationQueries.add(firstStr + " " + secondStr);
                }
            }
            if (StringUtils.equalsAnyIgnoreCase(firstStr, "Что делать, если")) {
                for (String secondStr : cimulationQueriesFourPart) {
                    cimulationQueries.add(firstStr + " " + secondStr);
                }
            }
            if (StringUtils.equalsAnyIgnoreCase(firstStr, "болит горло")) {
                for (String secondStr : cimulationQueriesFivesPart) {
                    cimulationQueries.add(firstStr + " " + secondStr);
                }
            }
            if (StringUtils.equalsAnyIgnoreCase(firstStr, "алиэкспресс")) {
                for (String secondStr : cimulationQueriesSixPart) {
                    cimulationQueries.add(firstStr + " " + secondStr);
                }
            }
        }
        return cimulationQueries;
    }

    private void timeout(Random random) {
        try {
            int timeout = 5000 + random.nextInt(10000);
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        PlServer p = new PlServer();
        p.setPliParserKinopoisk(new PlParserKinopoisk());
        p.setPliProxyServer(new PlProxyServer());
        p.getAllCinemasFromKinopoisk("");
//        p.emulationHumanActivity();
//        System.out.println("End emulation");
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
