package com.project.CinemaTickets.backend.ProxyServer;

import com.project.CinemaTickets.backend.ProxyServer.ProxyEntity.ProxyEntity;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    public static String ONE_OF_PROXY_SERVERS_SITE = "http://spys.one/";
    public static List<ProxyEntity> proxyListFromInternet = new ArrayList<>();
    private List<ProxyEntity> proxyListFromDatabase = new ArrayList<>();
    private volatile int counterGetterDocumentWithProxy = 0;

    private Logger logger = LoggerFactory.getLogger(PlProxyServer.class);

    @Override
    public List<ProxyEntity> getProxyFromInternet(String url) {
        logger.info("Start method getProxyFromInternet() at " + LocalDateTime.now());
        List<ProxyEntity> proxyList = new ArrayList<>();
        ProxyEntity proxy;
        if (url == null) {
            Document proxyDocument = getHttpDocumentFromInternet(ONE_OF_PROXY_SERVERS_SITE);
            Elements elements = proxyDocument.select("tr.spy1xx");
            for (Element element : elements) {
                proxy = new ProxyEntity();
                String ip_port = element.select("font.spy14").text();
                String type = element.select("font.spy1").text();
                proxy.setIp_address(selectIp(ip_port));
                proxy.setPort(selectPort(ip_port));
                proxy.setType(selectType(type));
                proxyList.add(proxy);
            }
        } else {
            Document proxyDocument = getHttpDocumentFromInternet(url);
        }
        logger.info("End of method getProxyFromInternet() at " + LocalDateTime.now());
        return proxyList;
    }

    @Override
    public List<ProxyEntity> getProxyFromDatabase() {
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
        if (counterGetterDocumentWithProxy > 20) {
            counterGetterDocumentWithProxy = 0;
            logger.error("Method fail! Document is not found!");
            //throw new DocumentNotFoundWithProxyException("Документ не найден");
            return Jsoup.parse("Документ не найден");
        }

        List<ProxyEntity> proxyList = getProxyFromDatabase();
        proxyList.addAll(proxyListFromInternet);
        if (proxyList.size() < 2) {
            proxyList.add(new ProxyEntity("61.5.134.13","9999", "SOCKS"));
            proxyList.add(new ProxyEntity("2.234.226.32","17779", "SOCKS"));
            proxyList.add(new ProxyEntity("188.191.29.141","48678", "HTTP"));
            proxyList.add(new ProxyEntity("80.255.91.38","60488", "HTTP"));
        }

        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(proxyList.size());
        ProxyEntity proxyServer = proxyList.get(index);

        String ip = proxyServer.getIp_address();
        String port = proxyServer.getPort();
        String type = proxyServer.getType();
        Proxy proxy = null;

        SocketAddress proxyAddr = new InetSocketAddress(ip, Integer.parseInt(port));

        if (StringUtils.contains(type, "SOCKS")) {
            proxy = new Proxy(Proxy.Type.SOCKS, proxyAddr);
        } else if (StringUtils.contains(type, "HTTP")) {
            proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
        }

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

    @Override
    public String createURLFromQueryWithGoogleForProxyServer(String url, boolean forEmulationActivity) throws IOException {
        logger.info("Start method createURLFromQueryWithGoogleForProxyServer() at " + LocalDateTime.now() + " - with url: " + url);
        int counter = 0; //необходим для выбора рандомной ссылки из поиска при эмуляции
        String urlFromGoogle = null;
        String titleQuery = null;
        Document googleHTMLdoc = getHttpDocumentFromInternet(url);
        Elements elements = googleHTMLdoc.select("div#search");
        int randomIndex = new Random(System.currentTimeMillis()).nextInt(10);
        for (Element element : elements.select("div.g")) {
            counter++;
            urlFromGoogle = element.getElementsByTag("a").attr("href");
            titleQuery = element.select("div.s").text();
            if (forEmulationActivity && isContains(url, titleQuery) && (counter == randomIndex)){
                int firstIndex = urlFromGoogle.indexOf("http");
                int lastIndex = urlFromGoogle.indexOf("/&");
                urlFromGoogle = urlFromGoogle.substring(firstIndex, lastIndex+1);
                return urlFromGoogle;
            } else if (!forEmulationActivity) {
                //TODO: продумать, для чего в прокси-сервере может понадобиться получение ссылки, кроме как для эмуляции активности
            }
            urlFromGoogle = null;
        }
        logger.info("End of method createURLFromQueryWithGoogleForProxyServer() at " + LocalDateTime.now() + " - with result: " + urlFromGoogle);
        return urlFromGoogle;
    }

    @Override
    public String createURLFromQueryWithYandexForProxyServer(String url, boolean forEmulationActivity) throws IOException {
        logger.info("Start method createURLFromQueryWithYandexForProxyServer() at " + LocalDateTime.now() + " - with query: " + url);
        int counter = 0; //необходим для выбора рандомной ссылки из поиска при эмуляции
        String urlFromYandex = null;
        String titleQuery = null;
        Document yandexHTMLdoc = getHttpDocumentFromInternet(url);
        Elements elements = yandexHTMLdoc.select("ul.serp-list.serp-list_left_yes");
        int randomIndex = new Random(System.currentTimeMillis()).nextInt(10);
        for (Element element : elements.select("li.serp-item")) {
            counter++;
            urlFromYandex = element.getElementsByTag("a").attr("href");
            titleQuery = element.select("span.extended-text__short").text();
            if (forEmulationActivity && isContains(url, titleQuery) && (counter == randomIndex)){
                int firstIndex = urlFromYandex.indexOf("http");
                urlFromYandex = urlFromYandex.substring(firstIndex);
                return urlFromYandex;
            } else if (!forEmulationActivity) {
                Elements secondElements = element.select("a.link.link_theme_outer.path__item.i-bem");
                //TODO: продумать, для чего в прокси-сервере может понадобиться получение ссылки, кроме как для эмуляции активности
            }
            urlFromYandex = null;
        }
        logger.info("End of method createURLFromQueryWithYandexForProxyServer() at " + LocalDateTime.now() + " - with result: " + urlFromYandex);
        return urlFromYandex;
    }

    @Override
    public String createUrlFromQueryForProxyServer(String queryForUrl, boolean forEmulationActivity) throws IOException {
        logger.info("Start method createUrlFromQueryForProxyServer() at " + LocalDateTime.now() + " - with queryForUrl: " + queryForUrl);
        String urlFromGoogle = null;
        String urlFromYandex = null;

        StringBuffer urlQueryForYandex = new StringBuffer();
        String[] wordsFromQuery = queryForUrl.split(" ");
        urlQueryForYandex.append("https://yandex.ru/search/?lr=213&text=");
        for (String word : wordsFromQuery) {
            urlQueryForYandex.append(word + "+");
        }
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(99);
        urlQueryForYandex.append("&lr=213&suggest_reqid=95075349" + String.valueOf(index) +"541506669920" + String.valueOf(index) + "069071226");
        urlFromYandex = createURLFromQueryWithYandexForProxyServer(urlQueryForYandex.toString(), forEmulationActivity);

        if (urlFromYandex == null) {
            StringBuffer urlQueryForGoogle = new StringBuffer();
            urlQueryForGoogle.append("https://www.google.com/search?q=");
            for (String word : wordsFromQuery) {
                urlQueryForGoogle.append(word + "+");
            }
            urlQueryForGoogle.append("&rlz=1C1GCEU_ruRU8" + String.valueOf(index) +"RU823&oq=regbnm+ltitde.+lhtkm&aqs=chrome.1."
                    + String.valueOf(index) +"i57j0j69i60.5128j0j4&sourceid=chrome&ie=UTF-8");
            urlFromGoogle = createURLFromQueryWithGoogleForProxyServer(urlQueryForGoogle.toString(), forEmulationActivity);
        }

        return urlFromYandex != null ? urlFromYandex : urlFromGoogle;
    }

    private boolean isContains(String url, String titleQuery) {
        if (StringUtils.contains(titleQuery,"-")) {
            titleQuery.replaceAll("-", " ");
        }
        if (StringUtils.contains(titleQuery,":")) {
            titleQuery.replaceAll(":", " ");
        }
        if (StringUtils.contains(titleQuery,".")) {
            titleQuery.replaceAll(".", " ");
        }
        if (StringUtils.contains(titleQuery,",")) {
            titleQuery.replaceAll(",", " ");
        }
        if (StringUtils.contains(titleQuery,"!")) {
            titleQuery.replaceAll("!", " ");
        }
        if (StringUtils.contains(titleQuery,",")) {
            titleQuery.replaceAll(",", " ");
        }
        if (StringUtils.contains(titleQuery,";")) {
            titleQuery.replaceAll(";", " ");
        }
        if (StringUtils.contains(titleQuery,"/")) {
            titleQuery.replaceAll("/", " ");
        }
        if (StringUtils.contains(titleQuery,"?")) {
            titleQuery.replaceAll("\\?", " ");
        }
        if (StringUtils.contains(titleQuery,"\"")) {
            titleQuery.replaceAll("\"", " ");
        }
        if (StringUtils.contains(titleQuery,"'")) {
            titleQuery.replaceAll("'", " ");
        }
        if (StringUtils.contains(titleQuery,"+")) {
            titleQuery.replaceAll("\\+", " ");
        }
        String[] titleAueryArray = titleQuery.split(" ");
        if (url != null && !url.isEmpty()) {
            url = url.substring(url.indexOf("="));
        }
        int counterMatching = 0;
        for (String str : titleAueryArray) {
            if (StringUtils.contains(url, str)) {
                counterMatching++;
            }
            if (counterMatching == 2) {
                return true;
            }
        }
        return false;
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
            logger.error("### ERROR ###" + e.getLocalizedMessage());
            logger.info("Method openConnection() FAILED at" + LocalDateTime.now() + " - with url: " + url + ", proxy: " + proxy);
            try {
                return new URL("https://yandex.ru").openConnection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private StringBuilder createStringDocument (URLConnection connection, StringBuilder stringDocument) {
        logger.info("Start method createStringDocument() at " + LocalDateTime.now());
        stringDocument = new StringBuilder();
        BufferedReader reader;
        try {
            /*
            -------------------------------------------------------------------------------------
             */
            Random random = new Random(System.currentTimeMillis());
            int num = random.nextInt(10);
//            connection.setRequestProperty("Cookie", "ys=c_chck.303" + num + "506" + num + "4; mda2_beacon=15" + num + "33571" + num + "8181; _ym_visorc_5" + num + "332" + num + "06=b; _ym_isad=2; crookie=Qknpr3OyI1JfXT50IsACbd" + num + "S1aRxedihnfrYpwN56lVKbEpIBhZATXh8qLhw47/mCiVjn8o6CLlQCXyKzrVUhWLouPg=; ya_sess_id=noauth:1563" + num + "57138; _ym_d=1563357079; yandexuid=81377" + num + "4761563" + num + "69214; mda=0; _ym_visorc_226" + num + "394" + num + "=b; mda_exp_enabled=1; cmtchd=MTU" + num + "MzM1NzE0MzQzNw==; _ym_uid=156335707" + num + "375756538; i=sJw7tHGq7UlMuiI3tcZWun" + num + "kpISGsA0eCyN8jhUQV" + num + "HsKIxFs2uPzWXymJsd8jS+BOG38n8n9/kdMi5+r3i+tPyo2uY=; _ym_visorc_10630330=w; _ym_wasSynced=%7B%22time%22%3A156" + num + "357078670%2C%22params%22%3A%7B%22eu%22%3A0%7D%2C%22bkParams%" + num + "2%3A%7B%7D%7D; spravka=dD0xNTMxODIyODY5O2k9MTg1Ljg5LjguMTQ2O3U9MTUzMTgyMjg2OTAwNDM5NDcwMDtoPTU2YzQxNzM5NmRjNTkzZWY1ZjczODEyNzAwNTNjYTAz; user-geo-region-id=213; user-geo-country-id=2; desktop_session_key=7208ac854c8c0ba" + num + "a4bb861fcced008e96bb9440a10662bf001b72bef97" + num + "98a29937346da2b97f5a27dacad3" + num + "bec2e575583f0153b498e548bdf6bc77405ee51b1354327dd68521918f1f25df4897b01b969de62b760f22705f3ad69cc323e5b; desktop_session_key.sig=t-" + num + "KoGZDclotGuo" + num + "r0Baroji" + num + "To; yandex_plus_metrika_cookie=true");
//            // Куки выше взяты после ввода капчи. Разобраться с ними.
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            connection.setRequestProperty("Cookie", "secure=1563637166-745e6332ecc4c711be40b59db238d21b; sid=8c8k5n4aaqbqqnd269pq4chnb5; sign=03c5d85dcfa918b7fe01535d21ba5951");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
            connection.setRequestProperty("Referer", "https://www.google.com/");
            /*
            --------------------------------------------------------------------------------------
             */

            connection.connect();
            Thread.sleep(5000);
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

        } catch (IOException | InterruptedException ex) {
            logger.info("Method createStringDocument() is FAILED with proxy tunnel");
            logger.error(LocalDateTime.now() + ": " + ex.toString());
        }
        logger.info("End of method createStringDocument() at " + LocalDateTime.now());
        return stringDocument;
    }

    private String selectIp(String ip_port) {
        return ip_port.substring(0, ip_port.indexOf(":"));
    }

    private String selectPort(String ip_port) {
        if (ip_port.contains(" ")) {
            String port = ip_port.substring(ip_port.indexOf(":") + 1, ip_port.indexOf(" "));
            if (port.contains(" ")) {
                return port.trim();
            }
            return port;
        } else if (ip_port.contains("!")) {
            return ip_port.substring(ip_port.indexOf(":") + 1, ip_port.indexOf("!") - 1);
        } else
            return ip_port.substring(ip_port.indexOf(":") + 1);
    }

    private String selectType (String type) {
        if (type.contains(" ")) {
            return type.substring(0, type.indexOf(" ") + 1);
        } else {
            return type;
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        PlProxyServer p = new PlProxyServer();
        p.getProxyFromInternet(null);

//        Document googleHTMLdoc = Jsoup.connect("https://hidemyna.me/ru/proxy-list/")
//                .userAgent("Mozilla/3.0 Chrome/32.0.3770.100 Safari/237.36")
//                .referrer("http://www.google.ru")
//                .get();
//        System.out.println(googleHTMLdoc);
    }
}
