package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.ruCaptchaAuto;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class RuCaptchaImpl implements RuCaptcha {
    public static boolean ruCaptchaEnable = true; //TODO: Добавить в файл конфигурации и тянуть оттуда.
    //https://ru.stackoverflow.com/questions/496727/%D0%9E%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%BA%D0%B0-%D0%BA%D0%B0%D0%BF%D1%87%D0%B8-%D0%BD%D0%B0-%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81-%D0%B0%D0%BD%D1%82%D0%B8%D0%B3%D0%B5%D0%B9%D1%82java-%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0-error-zero-captcha-filesize
    @Override
    public String sendRequest(String captchaUrlImage) throws IOException {
        String requestKey = "";
        String userKey = "";
        String method = "base64";
        StringBuilder url = new StringBuilder("https://rucaptcha.com/in.php?method=");
        url.append(method).append("&key=").append(userKey).append("&regsense=1");
        String imgUrlAfterEncode = encodeToString(captchaUrlImage, "jpg");
        url.append("&file=" + imgUrlAfterEncode);
        HttpURLConnection connection = (HttpURLConnection) new URL(url.toString()).openConnection();
        connection.setRequestMethod("POST");
        //connection.setDoOutput(true);
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        return requestKey;
    }

    @Override
    public String getResponse(String key) {
        String answer = "";
        return answer;
    }

    private String encodeToString(String url, String type) throws IOException {
        URL connection = new URL(url);
        HttpURLConnection urlconn;
        urlconn = (HttpURLConnection) connection.openConnection();
        urlconn.setRequestMethod("GET");
        urlconn.connect();
        InputStream in = null;
        in = urlconn.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        Base64 encoder = new Base64();
        String imageString = encoder.encodeToString(response);

//        BufferedImage image = ImageIO.read(new URL(url));
//        String imageString = null;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//        try {
//            ImageIO.write(image, type, bos);
//            byte[] imageBytes = bos.toByteArray();
//
//            Base64 encoder = new Base64();
//            imageString = encoder.encodeToString(imageBytes);
//
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return imageString;
    }

    public static void main(String[] args) throws IOException {
        RuCaptchaImpl ru = new RuCaptchaImpl();
        String key = ru.sendRequest("http://api.vk.com/captcha.php?sid=579356847070&s=1");
    }
}
