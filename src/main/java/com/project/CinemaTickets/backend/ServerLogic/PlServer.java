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
        return null;
    }

    @Override
    public Cinema getCinemaForDBFromKinopoisk(int cinemaId, String date) {
        return null;
    }

    @Override
    public void emulationHumanActivity() {

    }

    private ArrayList<String> createIdCinemas() {
        ArrayList<String> idList = new ArrayList<>();
        for (int i = 280268; i < 281250; i++){
            idList.add(String.valueOf(i));
        }
        return idList;
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
