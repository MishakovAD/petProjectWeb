package com.project.CinemaTickets.backend.ProxyServer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PlProxyServer implements PliProxyServer {
    public static String ONE_OF_PROXY_SERVERS_SITE = "https://free.proxy-sale.com/?proxy_page=";
    private List<String> proxyListFromDatabase = new ArrayList<>();
    private volatile int counterGetterDocumentWithProxy = 0;

    private Logger logger = LoggerFactory.getLogger(PlProxyServer.class);

    @Override
    public List<String> getProxyFromInternet(String url) {
        List<String> proxyList = new ArrayList<>();

        return null;
    }

    @Override
    public List<String> getProxyFromDatabase() {
        if (proxyListFromDatabase.size() < 5) {
            //download from database and add to list
            //...
            return proxyListFromDatabase;
        } else {
            return proxyListFromDatabase;
        }

    }

    @Override
    public String createIpFromPairIP_Port(String ip_port) {
        //TODO: сделать проверку на маску ip:port по паттерну
        if (ip_port.contains(":")) {
            return ip_port.substring(0, ip_port.indexOf(":"));
        } else if (ip_port.contains(",")) {
            return ip_port.substring(0, ip_port.indexOf(","));
        } else {
            return "1.0.177.41";
        }

    }

    @Override
    public String createPortFromPairIP_Port(String ip_port) {
        if (ip_port.contains(":")) {
            return ip_port.substring(ip_port.indexOf(":") + 1);
        } else if (ip_port.contains(",")) {
            return ip_port.substring(ip_port.indexOf(",") + 1);
        } else {
            return "8080";
        }
    }

    @Override
    public boolean addProxyToDatabase(List<String> proxyList) {
        return false;
    }

    @Override
    public boolean isExistProxyOnDatabase(String proxy) {
        return false;
    }

    @Override
    public boolean deleteProxyFromDatabase(String proxy) {
        return false;
    }

    @Override
    public Document getHttpDocumentFromInternet(String url) {
        StringBuilder stringDocument = new StringBuilder();
        URLConnection connection;

        connection = openConnection(url, null);
        stringDocument = createStringDocument(connection, stringDocument);

        if (isCorrectDownloadDocument(stringDocument.toString())) {
            Document document = Jsoup.parse(stringDocument.toString());
            return document;
        } else {
            Document document = getHttpDocumentFromInternetWithProxy(url);
            return document;
        }
    }

    @Override
    public Document getHttpDocumentFromInternetWithProxy(String url) {
        counterGetterDocumentWithProxy++;
        if (counterGetterDocumentWithProxy > 100) {
            //throw new DocumentNotFoundWithProxyException("Документ не найден");
            return Jsoup.parse("Документ не найден");
        }

        List<String> proxyList = getProxyFromDatabase();
        if (proxyList.size() < 2) {
            proxyList.add("1.0.177.41:8080");
        }

        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(proxyList.size() + 1);
        String proxyServer = proxyList.get(index);

        String ip = createIpFromPairIP_Port(proxyServer);
        String port = createPortFromPairIP_Port(proxyServer);

        SocketAddress proxyAddr = new InetSocketAddress(ip, Integer.parseInt(port));
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);

        StringBuilder stringDocument = new StringBuilder();
        URLConnection connection;

        connection = openConnection(url, proxy);

        if (connection != null) {
            stringDocument = createStringDocument(connection, stringDocument);
        }

        if (isCorrectDownloadDocument(stringDocument.toString())) {
            Document document = Jsoup.parse(stringDocument.toString());
            return document;
        } else {
            Document document = getHttpDocumentFromInternetWithProxy(url);
            return document;
        }

    }

    @Override
    public boolean isCorrectDownloadDocument(String document) {
        //TODO: Посмотреть, какие сообщения могут выводиться, а так же сделать проверку на типы ссылок для парсеров и прочее.
        if (document.contains("адрес заблокарован")) {
            return false;
        } else {
            return true;
        }
    }

    private URLConnection openConnection(String url, Proxy proxy) {
        try {
            if (proxy == null) {
                return new URL(url).openConnection();
            } else {
                return new URL(url).openConnection(proxy);
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    private StringBuilder createStringDocument (URLConnection connection, StringBuilder stringDocument) {
        stringDocument = new StringBuilder();
        BufferedReader reader;
        try {
            Random random = new Random(System.currentTimeMillis());
            int version = random.nextInt(9);
            connection.setRequestProperty("User-Agent", "Mozilla/5." + String.valueOf(version)
                    + " (Windows NT 10.0; Win64; x64)" +  " AppleWebKit/53" + String.valueOf(version) + ".36 (KHTML, like Gecko)"
                    + " Chrome/7" + String.valueOf(version) + ".0.37" + String.valueOf(version) + "0.100 Safari/5" + String.valueOf(version) + "7.36");
            connection.setRequestProperty("Referer", "https://www.google.com/");
            connection.connect();
            Thread.sleep(15000);
            reader  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            String line;
            while ((line = reader.readLine()) != null) {
                stringDocument.append(line);
            }

        } catch (IOException | InterruptedException ex) {
            logger.error(LocalDateTime.now() + ": " + ex.toString());
        }
        return stringDocument;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        PlProxyServer p = new PlProxyServer();
        System.out.println(p.getHttpDocumentFromInternet("https://www.kinopoisk.ru/film/1008445/"));

//        Document googleHTMLdoc = Jsoup.connect("https://hidemyna.me/ru/proxy-list/")
//                .userAgent("Mozilla/3.0 Chrome/32.0.3770.100 Safari/237.36")
//                .referrer("http://www.google.ru")
//                .get();
//        System.out.println(googleHTMLdoc);
    }
}
