package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlHttpClient implements PliHttpClient {
    private Logger logger = LoggerFactory.getLogger(PlHttpClient.class);

    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/536.36";
    public static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
    public static String CHECK_ANTI_SPAM = "Если вы&nbsp;видите эту страницу, значит с&nbsp;вашего IP-адреса поступило необычно много запросов.";


    @Override
    public Document getDocumentFromInternet(String url) throws IOException {
        String answerCaptcha;
        StringBuilder htmlDocumentAtString = new StringBuilder();
        HttpClient httpClient = HttpClientBuilder.create().build();
        ResponseHandler responseHandler = new BasicResponseHandler();
        HttpContext context = new HttpClientContext();

        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", ACCEPT);
        request.setHeader("User-Agent", USER_AGENT);

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request, context);

        List<URI> redirectionList = ((HttpClientContext) context).getRedirectLocations();

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpServletResponse.SC_OK) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                htmlDocumentAtString.append(line);
            }
        }

        if (StringUtils.containsIgnoreCase(htmlDocumentAtString, CHECK_ANTI_SPAM) && redirectionList.size() > 0) {
            answerCaptcha = getAnswerUrlForCaptcha(htmlDocumentAtString);
            HttpGet requestCaptcha = new HttpGet(answerCaptcha);
            requestCaptcha.setHeader("referer", redirectionList.get(0).toString());
        }

        response.close();
        Document htmlDocument = Jsoup.parse(htmlDocumentAtString.toString());
        return htmlDocument;
    }

    @Override
    public String getAnswerUrlForCaptcha(StringBuilder capthaDocument) {
        return null;
    }

    @Override
    public Map<String, String> getCookies(HttpResponse response) {
        Map<String, String> cookiesMap = new HashMap<>();
        Arrays.stream(response.getAllHeaders()).forEach(header -> {
            if (StringUtils.containsIgnoreCase(header.getName(), "cookie")) {

            }
        });
        return null;
    }


    public static void main(String[] args) throws IOException {
        PlHttpClient pl = new PlHttpClient();
        while (true) {
            pl.getDocumentFromInternet("https://www.kinopoisk.ru/afisha/city/1/cinema/280891/");
        }
    }
}
