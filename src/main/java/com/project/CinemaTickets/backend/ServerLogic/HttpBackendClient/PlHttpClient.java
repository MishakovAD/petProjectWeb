package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient;

import com.project.CinemaTickets.backend.Parser.PlParserKinopoisk;
import com.project.CinemaTickets.backend.Parser.PliParserKinopoisk;
import com.project.CinemaTickets.backend.ServerLogic.DAO.DAOHelperUtils.ConverterToImpl;
import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;
import com.project.CinemaTickets.backend.ServerLogic.DAO.HibernateUtils.HibernateDaoImpl;
import com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.ruCaptchaAuto.RuCaptcha;
import com.project.CinemaTickets.backend.config.ConfigBackend;
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

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.CinemaTickets.backend.constants.Constants.CHECK_ANTI_SPAM;
import static com.project.CinemaTickets.backend.constants.Constants.accept;
import static com.project.CinemaTickets.backend.constants.Constants.accept_encoding;
import static com.project.CinemaTickets.backend.constants.Constants.accept_language;
import static com.project.CinemaTickets.backend.constants.Constants.ruCaptchaEnable;
import static com.project.CinemaTickets.backend.constants.Constants.sec_fetch_cite;
import static com.project.CinemaTickets.backend.constants.Constants.sec_fetch_mode;
import static com.project.CinemaTickets.backend.constants.Constants.sec_fetch_user;
import static com.project.CinemaTickets.backend.constants.Constants.upgrade_insecure_requests;
import static com.project.CinemaTickets.backend.constants.Constants.user_agent;
import static com.project.CinemaTickets.backend.constants.Constants.authority;
import static com.project.CinemaTickets.backend.constants.Constants.scheme;
import static com.project.CinemaTickets.backend.constants.Constants.method;

@Component
public class PlHttpClient implements PliHttpClient {
    private Logger logger = LoggerFactory.getLogger(PlHttpClient.class);
    //TODO: подобные переменные вынести в контекст и состояние.
    public static String captchaImageUrl = "";
    public static String answerCaptchaFromController = "";
    public static String answerCaptchaFromRuCaptcha = "";

    @Override
    public Document getDocumentFromInternet(String url) throws IOException {
        logger.info("Start method getDocumentFromInternet() at " + LocalDateTime.now());
        StringBuilder htmlDocumentAtString = new StringBuilder();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpContext context = new HttpClientContext();
        HttpGet request = getRequestWithHeaders(url);

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request, context);
        List<URI> redirectionList = ((HttpClientContext) context).getRedirectLocations();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpServletResponse.SC_OK) {
            htmlDocumentAtString = readDocumentFromResponse(response);
        }

        if (StringUtils.containsIgnoreCase(htmlDocumentAtString, CHECK_ANTI_SPAM) && redirectionList.size() > 0) {
            String answerCaptchaUrl = getAnswerUrlForCaptcha(htmlDocumentAtString);
            HttpGet requestCaptcha = getRequestWithHeaders(answerCaptchaUrl);
            request.setHeader("referer", redirectionList.get(0).toString());
            redirectionList = new ArrayList<>();

            CloseableHttpResponse responseCaptcha = (CloseableHttpResponse) httpClient.execute(requestCaptcha, context);
            redirectionList = ((HttpClientContext) context).getRedirectLocations();
            htmlDocumentAtString = readDocumentFromResponse(responseCaptcha);
            responseCaptcha.close();
            if (responseCaptcha.getStatusLine().getStatusCode() == HttpServletResponse.SC_NOT_FOUND) {
                htmlDocumentAtString = new StringBuilder("Файл не найден. Ошибка 404.");
            }
            if (StringUtils.containsIgnoreCase(htmlDocumentAtString, CHECK_ANTI_SPAM) && redirectionList.size() > 0) {
                getDocumentFromInternet(url);
            }
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
        String key = getKeyFromCaptchaDoc(captchaDoc);
        String retpath = getRetpathFromCaptchaDoc(captchaDoc);
        url.append(key).append("&retpath=").append(retpath).append("&rep=");
        String srcImg = getScrImgFromCaptchaDoc(captchaDoc);
        captchaImageUrl = srcImg;

        waitAnswerToCapcha(srcImg);
        String answer = StringUtils.isEmpty(answerCaptchaFromController) ? answerCaptchaFromRuCaptcha : answerCaptchaFromController;
        answerCaptchaFromController = "";
        answerCaptchaFromRuCaptcha = "";
        captchaImageUrl = "";
        //-----------------------------------------------------------------------
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            answer = URLEncoder.encode(reader.readLine());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //------------------------------------------------------------------
        url.append(URLEncoder.encode(answer));
        logger.info("End of method getAnswerUrlForCaptcha() at " + LocalDateTime.now());
        return url.toString();
    }

    private String getScrImgFromCaptchaDoc(Document captchaDoc) {
        return captchaDoc
                .getElementsByAttributeValue("class", "image form__captcha")
                .attr("src");
    }

    private void waitAnswerToCapcha(String srcImg) {
        System.out.println(srcImg);
        if (ruCaptchaEnable) {
            String ruCaptchaResponseKey = null;
            try {
                ruCaptchaResponseKey = ruCaptcha.sendRequest(srcImg);
            } catch (IOException ex) {
                logger.error("ERROR at getAnswerUrlForCaptcha!", ex);
            }
            while (StringUtils.isEmpty(answerCaptchaFromRuCaptcha) || StringUtils.equals(answerCaptchaFromRuCaptcha, "CAPCHA_NOT_READY")) {
                try {
                    Thread.sleep(3500);
                    answerCaptchaFromRuCaptcha = ruCaptcha.getResponse(ruCaptchaResponseKey);
                } catch (InterruptedException | IOException ex) {
                    logger.error("ERROR on sleep at getAnswerUrlForCaptcha!", ex);
                }
                if (StringUtils.equals(answerCaptchaFromRuCaptcha, "ERROR_CAPTCHA_UNSOLVABLE")) {
                    answerCaptchaFromRuCaptcha = "Ответ не найден, нужна новая каптча";
                }

            }
        } else {
            while(StringUtils.isEmpty(answerCaptchaFromController)) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    logger.error("ERROR on sleep at getAnswerUrlForCaptcha!", ex);
                }
            }
        }
    }


    private HttpGet getRequestWithHeaders(String url) {
        HttpGet request = new HttpGet(url);
        request.setHeader("path", url.replaceAll("https://www.kinopoisk.ru/", ""));
        request.setHeader("accept", accept);
        request.setHeader("user-agent", user_agent);
        request.setHeader("authority", authority);
        request.setHeader("method", method);
        request.setHeader("scheme", scheme);
        request.setHeader("accept-encoding", accept_encoding);
        request.setHeader("accept-language", accept_language);
        request.setHeader("sec-fetch-mode", sec_fetch_mode);
        request.setHeader("sec-fetch-cite", sec_fetch_cite);
        request.setHeader("sec-fetch-user", sec_fetch_user);
        request.setHeader("upgrade-insecure-requests", upgrade_insecure_requests);
        return request;
    }

    private String getRetpathFromCaptchaDoc(Document captchaDoc) {
        return captchaDoc
                .getElementsByAttributeValue("name", "retpath")
                .attr("value")
                .replaceAll("/", "%2F")
                .replaceAll(":", "%3A")
                .replaceAll("\\?", "%3F");
    }

    private String getKeyFromCaptchaDoc(Document captchaDoc) {
        return captchaDoc
                .getElementsByAttributeValue("name", "key")
                .attr("value")
                .replaceAll("/", "%2F")
                .replaceAll(":", "%3A");
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

    //-----------------------------------------------------------------------------------
    private RuCaptcha ruCaptcha;

    @Inject
    public void setRuCaptcha(RuCaptcha ruCaptcha) {
        this.ruCaptcha = ruCaptcha;
    }


    public static void main(String[] args) throws IOException {
        PlHttpClient pl = new PlHttpClient();
        String str = "https://www.kinopoisk.ru/afisha/city/1/cinema/280268/";
        PliParserKinopoisk parser = new PlParserKinopoisk();
        Cinema cinema = parser.getCinemaFromDocument(pl.getDocumentFromInternet(str));
        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(cinema);
        ConverterToImpl converterTo = new ConverterToImpl();
        HibernateDaoImpl hib = new HibernateDaoImpl();
        while (true) {
            hib.saveCinemaMovieSessionObj(converterTo.getCinemaMovieSessionListCinemasList(cinemaList));
        }

    }
}
