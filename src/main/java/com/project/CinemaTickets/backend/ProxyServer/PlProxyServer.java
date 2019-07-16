package com.project.CinemaTickets.backend.ProxyServer;

import com.project.CinemaTickets.backend.ProxyServer.Exceptions.DocumentNotFoundWithProxyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if (proxyListFromDatabase.size() == 0) {
            //download from database and add to list
            //...
            return proxyListFromDatabase;
        } else {
            return proxyListFromDatabase;
        }

    }

    @Override
    public String createIpFromPairIP_Port(String ip_port) {
        return null;
    }

    @Override
    public String createPortFromPairIP_Port(String ip_port) {
        return null;
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
    public Document getHttpDocumentFromInternetWithoutProxy(String url) {
        StringBuilder stringDocument = new StringBuilder();
        BufferedReader reader;
        URLConnection connection;
        try {
            connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
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
            //throw new DocumentNotFoundWithProxyException();
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
        BufferedReader reader;
        URLConnection connection;
        try {

            connection = new URL(url).openConnection(proxy);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
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

        if (isCorrectDownloadDocument(stringDocument.toString())) {
            Document document = Jsoup.parse(stringDocument.toString());
            return document;
        } else {
            Document document = getHttpDocumentFromInternetWithProxy(url);
            return document;
        }

    }

    private StringBuilder createStringDocument (URLConnection connection, StringBuilder stringDocument) {
        stringDocument = new StringBuilder();
        BufferedReader reader;
        try {
            //Add rendom int for different version User-Agent
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
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

    @Override
    public boolean isCorrectDownloadDocument(String document) {
        return false;
    }


    public static void main(String[] args) throws IOException, InterruptedException {

//        Document googleHTMLdoc = Jsoup.connect("https://hidemyna.me/ru/proxy-list/")
//                .userAgent("Mozilla/3.0 Chrome/32.0.3770.100 Safari/237.36")
//                .referrer("http://www.google.ru")
//                .get();
//        System.out.println(googleHTMLdoc);
    }
}
