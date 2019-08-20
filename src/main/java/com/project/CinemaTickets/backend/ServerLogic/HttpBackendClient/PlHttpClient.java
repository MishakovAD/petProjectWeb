package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class PlHttpClient implements PliHttpClient {
    private Logger logger = LoggerFactory.getLogger(PlHttpClient.class);
    //TODO: Заполнить все хедеры
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/536.36";
    public static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
    public static String CHECK_ANTI_SPAM = "Если вы&nbsp;видите эту страницу, значит с&nbsp;вашего IP-адреса поступило необычно много запросов.";


    @Override
    public Document getDocumentFromInternet(String url) throws IOException {
        logger.info("Start method getDocumentFromInternet() at " + LocalDateTime.now());
        String answerCaptchaUrl;
        StringBuilder htmlDocumentAtString = new StringBuilder();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpContext context = new HttpClientContext();

        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", ACCEPT);
        request.setHeader("User-Agent", USER_AGENT);

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request, context);
        List<URI> redirectionList = ((HttpClientContext) context).getRedirectLocations();

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpServletResponse.SC_OK) {
            htmlDocumentAtString = readDocumentFromResponse(response);
        }

        if (StringUtils.containsIgnoreCase(htmlDocumentAtString, CHECK_ANTI_SPAM) && redirectionList.size() > 0) {
            //Создаем новый поток, который будет останавливать поток, что вызвал метод и ждать ввода каптчи, а затем продолжать работу.
            AtomicBoolean pause = new AtomicBoolean(true);
            Thread captchaThread = new Thread(() -> {
                System.out.println("captchaThread is starting, Thread = " + Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pause.set(false);
                //По задумке конструкция вначале заставляет ждать поток, который вызвал метод
                // (а то есть у воркера) до тех пор, пока не закончит свое выполнение этот метод, а данная конструкция пробуждает все потоки.
            }, "captchaThread");
            captchaThread.start();
            Thread.getAllStackTraces().forEach((threadKey, threadValue) -> {
                System.out.println("ThreadKey=" + threadKey + ", ThreadValue=" + threadValue);
                if (threadKey.getName().equals("workerThread")) {
                    while (pause.get()) {
                        System.out.println("****pause****");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //-----------------------------------------------------------------------------------------------------------------------
            //Вынести в отдельный метод, чтобы вызывать в потоке.
            answerCaptchaUrl = getAnswerUrlForCaptcha(htmlDocumentAtString);

            HttpGet requestCaptcha = new HttpGet(answerCaptchaUrl);
            requestCaptcha.setHeader("path", answerCaptchaUrl.replaceAll("https://www.kinopoisk.ru/", ""));
            requestCaptcha.setHeader("scheme","https");
            requestCaptcha.setHeader("Accept", ACCEPT);
            requestCaptcha.setHeader("accept-encoding", "gzip, deflate, br");
            requestCaptcha.setHeader("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            requestCaptcha.setHeader("referer", redirectionList.get(0).toString());
            requestCaptcha.setHeader("upgrade-insecure-requests", "1");
            requestCaptcha.setHeader("user-agent", USER_AGENT);
            CloseableHttpResponse responseCaptcha = (CloseableHttpResponse) httpClient.execute(requestCaptcha, context);

            htmlDocumentAtString = readDocumentFromResponse(responseCaptcha);
            responseCaptcha.close();
            //-----------------------------------------------------------------------------------------------------------------------------
        }

        response.close();
        Document htmlDocument = Jsoup.parse(htmlDocumentAtString.toString());
        logger.info("End of method getDocumentFromInternet() at " + LocalDateTime.now());
        return htmlDocument;
    }

    @Override
    public String getAnswerUrlForCaptcha(StringBuilder captchaDocument) {
        logger.info("Start method getAnswerUrlForCaptcha() at " + LocalDateTime.now());
        StringBuilder url = new StringBuilder("https://www.kinopoisk.ru/checkcaptcha?key=");
        Document captchaDoc = Jsoup.parse(captchaDocument.toString());
        String key = captchaDoc
                .getElementsByAttributeValue("name", "key")
                .attr("value")
                .replaceAll("/", "%2F")
                .replaceAll(":", "%3A");
        String retpath = captchaDoc
                .getElementsByAttributeValue("name", "retpath")
                .attr("value")
                .replaceAll("/", "%2F")
                .replaceAll(":", "%3A")
                .replaceAll("\\?", "%3F");
        url.append(key).append("&retpath=").append(retpath).append("&rep=");
        String answer = "";
        //TODO: Это место преобразовать в ожидание ввода результатов с сайта.
        String srcImg = captchaDoc
                .getElementsByAttributeValue("class", "image form__captcha")
                .attr("src");
        System.out.println(srcImg);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = URLEncoder.encode(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------------------
        url.append(answer);
        logger.info("End of method getAnswerUrlForCaptcha() at " + LocalDateTime.now());
        return url.toString();
    }

    @Override
    public Map<String, String> getCookies(HttpResponse response) {
        logger.info("Start method getCookies() at " + LocalDateTime.now());
        Map<String, String> cookiesMap = new HashMap<>();
        Arrays.stream(response.getAllHeaders()).forEach(header -> {
            if (StringUtils.containsIgnoreCase(header.getName(), "cookie")) {

            }
        });
        logger.info("End of method getCookies() at " + LocalDateTime.now());
        return null;
    }

    private StringBuilder readDocumentFromResponse(HttpResponse response) throws IOException {
        logger.info("Start method readDocumentFromResponse() at " + LocalDateTime.now());
        StringBuilder htmlDocAtString = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        while ((line = reader.readLine()) != null) {
            htmlDocAtString.append(line);
        }
        logger.info("End of method readDocumentFromResponse() at " + LocalDateTime.now());
        return htmlDocAtString;
    }


    public static void main(String[] args) throws IOException {
        PlHttpClient pl = new PlHttpClient();
        while (true) {
            pl.getDocumentFromInternet("https://www.kinopoisk.ru/afisha/city/1/cinema/280891/");
        }
    }
}
