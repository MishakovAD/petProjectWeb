package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.ruCaptchaAuto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.project.CinemaTickets.backend.constants.ConstantsImpl.userKey;

@Component
public class RuCaptchaImpl implements RuCaptcha {
    private Logger logger = LoggerFactory.getLogger(RuCaptchaImpl.class);

    @Override
    public String sendRequest(String captchaUrlImage) throws IOException {
        logger.debug("Start sendRequest() from RuCaptchaImpl.class");
        String requestKey = "";
        String method = "base64";
        StringBuilder url = new StringBuilder("https://rucaptcha.com/in.php?method=");
        url.append(method).append("&key=").append(userKey).append("&regsense=1");
        String imgUrlAfterEncode = URLEncoder.encode(encodeToString(captchaUrlImage));
        url.append("&body=" + imgUrlAfterEncode);
        //url.append("&debug_dump=1"); //для того, чтобы увидеть, что получает сервер
        String response = readResponse(url.toString(), imgUrlAfterEncode, true);

        System.out.println(response);
        if (StringUtils.containsIgnoreCase(response, "OK")) {
            requestKey = response.substring(response.indexOf("OK|")+3);
        } else {
            requestKey = response;
        }

        return requestKey;
    }

    @Override
    public String getResponse(String key) throws IOException {
        logger.debug("Start getResponse() from RuCaptchaImpl.class");
        String answer = null;
        String response = null;
        StringBuilder responseUrl = new StringBuilder("https://rucaptcha.com/res.php");
        responseUrl.append("?key=").append(userKey).append("&action=get&id=").append(key);
        response = readResponse(responseUrl.toString(), "", false);
        if (StringUtils.containsIgnoreCase(answer, "OK")) {
            answer = response.substring(response.indexOf("OK|")+3);
        } else {
            answer = response;
        }
        return answer;
    }

    private String readResponse(String url, String img, boolean methodPost) throws IOException {
        logger.debug("Start readResponse() from RuCaptchaImpl.class");
        StringBuffer response = new StringBuffer();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        if (methodPost) {
            connection.setRequestMethod("POST");
        } else {
            connection.setRequestMethod("GET");
        }
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST/GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private String encodeToString(String url) throws IOException {
        logger.debug("Start encodeToString() from RuCaptchaImpl.class");
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = null;
        inputStream = connection.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1 != (n = inputStream.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        inputStream.close();
        byte[] response = out.toByteArray();
        Base64 encoder = new Base64();
        String imageString = encoder.encodeToString(response);
        return imageString;
    }
}
