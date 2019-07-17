package com.project.CinemaTickets.backend.ProxyServer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
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
        logger.info("Start method getProxyFromDatabase() at " + LocalDateTime.now());
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
        logger.info("Start method createIpFromPairIP_Port() at " + LocalDateTime.now());
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
        logger.info("Start method createPortFromPairIP_Port() at " + LocalDateTime.now());
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
        logger.info("Start method getHttpDocumentFromInternet() at " + LocalDateTime.now() + " - with url: " + url);
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
        logger.info("Start method getHttpDocumentFromInternetWithProxy() at " + LocalDateTime.now() + " - with url: " + url);
        counterGetterDocumentWithProxy++;
        if (counterGetterDocumentWithProxy > 100) {
            logger.error("Method fail! Document is not found!");
            //throw new DocumentNotFoundWithProxyException("Документ не найден");
            return Jsoup.parse("Документ не найден");
        }

        List<String> proxyList = getProxyFromDatabase();
        if (proxyList.size() < 2) {
            proxyList.add("158.69.59.125:8888");
            proxyList.add("vashdosug.ru:80");
        }

        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(proxyList.size());
        String proxyServer = proxyList.get(index);

        String ip = createIpFromPairIP_Port(proxyServer);
        String port = createPortFromPairIP_Port(proxyServer);

        SocketAddress proxyAddr = new InetSocketAddress(ip, Integer.parseInt(port));
        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);

        StringBuilder stringDocument = new StringBuilder();
        URLConnection connection;

        connection = openConnection(url, proxy);

        if (connection != null) {
            logger.info("After openConnection() connection = " + connection.toString());
            stringDocument = createStringDocument(connection, stringDocument);
        }

        if (isCorrectDownloadDocument(stringDocument.toString())) {
            Document document = Jsoup.parse(stringDocument.toString());
            logger.info("Method getHttpDocumentFromInternetWithProxy() finished successful at " + LocalDateTime.now());
            return document;
        } else {
            logger.info("Method getHttpDocumentFromInternetWithProxy() fail at " + LocalDateTime.now() + " - let's start search with proxy");
            Document document = getHttpDocumentFromInternetWithProxy(url);
            return document;
        }

    }

    @Override
    public boolean isCorrectDownloadDocument(String document) {
        logger.info("Start method isCorrectDownloadDocument() at " + LocalDateTime.now());
        //TODO: Посмотреть, какие сообщения могут выводиться, а так же сделать проверку на типы ссылок для парсеров и прочее.
        if (document.equals("Файл не найден. Ошибка 404.")) {
            logger.info("Файл не найден! Но вернем корректный флаг.");
            return true;
        }

        if (document.isEmpty() || document.contains("адрес заблокарован")
                || document.contains("поступившие с вашего IP-адреса")
                || document.contains("пожалуйста, введите символы")
                || document.contains("запросы, поступившие с&nbsp;вашего IP-адреса")
                || document.contains("Нам очень жаль, но")
                || document.contains("продолжить поиск, пожалуйста, введите символы")) {
            logger.info("Document is NOT correct!");
            return false;
        } else {
            logger.info("Document is correct!");
            return true;
        }
    }

    private URLConnection openConnection(String url, Proxy proxy) {
        logger.info("Start method openConnection() at " + LocalDateTime.now() + " - with url: " + url + ", proxy: " + proxy);
        try {
            if (proxy == null) {
                return new URL(url).openConnection();
            } else {
                URL url_ = new URL(url);
                return (HttpsURLConnection) url_.openConnection(proxy);
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            logger.info("Method openConnection() FAILED at" + LocalDateTime.now() + " - with url: " + url + ", proxy: " + proxy);
        }
        return null;
    }

    private StringBuilder createStringDocument (URLConnection connection, StringBuilder stringDocument) {
        logger.info("Start method createStringDocument() at " + LocalDateTime.now());
        stringDocument = new StringBuilder();
        BufferedReader reader;
        try {
//            connection.setRequestProperty("User-Agent", "Links (2.8; Linux 3.13.0-24-generic x86_64; GNU C 4.8.2; text)");
//            connection.setRequestProperty("Referer", "https://www.google.com/");
            /*
            -------------------------------------------------------------------------------------
             */
            connection.setRequestProperty("authority", "hidemyna.me");
            connection.setRequestProperty("method", "GET");
            connection.setRequestProperty("scheme", "https");
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
//            connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
//            connection.setRequestProperty("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Cookie", "ys=c_chck.303550694; mda2_beacon=1563357138181; _ym_visorc_52332406=b; _ym_isad=2; crookie=Qknpr3OyI1JfXT50IsACbd9S1aRxedihnfrYpwN56lVKbEpIBhZATXh8qLhw47/mCiVjn8o6CLlQCXyKzrVUhWLouPg=; ya_sess_id=noauth:1563357138; _ym_d=1563357079; yandexuid=8137754761563269214; mda=0; _ym_visorc_22663942=b; mda_exp_enabled=1; cmtchd=MTU2MzM1NzE0MzQzNw==; _ym_uid=1563357079375756538; i=sJw7tHGq7UlMuiI3tcZWun5kpISGsA0eCyN8jhUQV4HsKIxFs2uPzWXymJsd8jS+BOG38n8n9/kdMi5+r3i+tPyo2uY=; _ym_visorc_10630330=w; _ym_wasSynced=%7B%22time%22%3A1563357078670%2C%22params%22%3A%7B%22eu%22%3A0%7D%2C%22bkParams%22%3A%7B%7D%7D; spravka=dD0xNTMxODIyODY5O2k9MTg1Ljg5LjguMTQ2O3U9MTUzMTgyMjg2OTAwNDM5NDcwMDtoPTU2YzQxNzM5NmRjNTkzZWY1ZjczODEyNzAwNTNjYTAz; user-geo-region-id=213; user-geo-country-id=2; desktop_session_key=7208ac854c8c0ba5a4bb861fcced008e96bb9440a10662bf001b72bef97298a29937346da2b97f5a27dacad37bec2e575583f0153b498e548bdf6bc77405ee51b1354327dd68521918f1f25df4897b01b969de62b760f22705f3ad69cc323e5b; desktop_session_key.sig=t-6KoGZDclotGuo5r0Baroji3To; yandex_plus_metrika_cookie=true");
            // Куки выше взяты после ввода капчи. Разобраться с ними.
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

//            connection.setDoOutput(true);
//            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//            wr.write("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
//            wr.flush();
//            wr.close();
            /*
            --------------------------------------------------------------------------------------
             */

            connection.connect();
            //Thread.sleep(15000);
            try {
                reader  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            } catch (FileNotFoundException ex) {
                logger.info("Файл не найден. Ошибка 404.");
                stringDocument.append("Файл не найден. Ошибка 404.");
                logger.info("End of method createStringDocument() with FileNotFound exception at " + LocalDateTime.now());
                return stringDocument;
            }


            String line;
            while ((line = reader.readLine()) != null) {
                stringDocument.append(line);
            }

        } catch (IOException ex) {
            logger.info("Method createStringDocument() is FAILED with proxy tunnel");
            logger.error(LocalDateTime.now() + ": " + ex.toString());
        }
        logger.info("End of method createStringDocument() at " + LocalDateTime.now());
        return stringDocument;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        PlProxyServer p = new PlProxyServer();
//        System.out.println(p.getHttpDocumentFromInternetWithProxy("https://2ip.ru"));
//        System.out.println(p.getHttpDocumentFromInternet("https://2ip.ru"));
//        System.out.println(p.getHttpDocumentFromInternet("https://hidemyna.me/ru/proxy-list/?start=128#list"));
        //for (int i = 280268; i < 281250; i++){
        for (int i = 280280; i < 281250; i++){
            Document doc = p.getHttpDocumentFromInternet("https://www.kinopoisk.ru/afisha/city/1/cinema/" + String.valueOf(i) + "/");
            System.out.println(doc.select("h1.level2").text());
        }


//        Document googleHTMLdoc = Jsoup.connect("https://hidemyna.me/ru/proxy-list/")
//                .userAgent("Mozilla/3.0 Chrome/32.0.3770.100 Safari/237.36")
//                .referrer("http://www.google.ru")
//                .get();
//        System.out.println(googleHTMLdoc);
    }
}
