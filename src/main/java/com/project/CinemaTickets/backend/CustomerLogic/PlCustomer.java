package com.project.CinemaTickets.backend.CustomerLogic;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
import java.util.*;

public class PlCustomer implements PliCustomer {
    public static void main(String[] args) throws IOException {
        PlCustomer p = new PlCustomer();
        p.createRequestWithCookie();

    }

    public void createRequestWithCookie() throws IOException {
        try {
        URLConnection conn = new URL("https://www.afisha.ru/msk/schedule_cinema_product/232355/").openConnection();
//        List<String> coockies = conn.getHeaderFields().get("Set-Cookie");
//        for (String coockie : coockies) {
//            System.out.println(coockie);
//        }
//        conn.setRequestProperty("referer", "https://www.afisha.ru/msk/schedule_cinema_product/232355/");
//        conn.setRequestProperty("cookie", "ruid=ugsAAOpdH102pdFvARQbAAB=; expires=Thu, 31-Dec-37 23:55:55 GMT; domain=.afisha.ru; path=/msl_s=2; domain=.afisha.ru; path=/; samesite=lax");

        InputStream stream = conn.getInputStream();
        BufferedInputStream in = new BufferedInputStream(stream);
        int length = 0;
        byte[] data = new byte[1024];
        while ((length = in.read(data)) != -1) {
            //System.out.write(data);
        }
        in.close();
    }
        catch (    MalformedURLException e) {
        e.printStackTrace();
    }
        catch (IOException e) {
        e.printStackTrace();
    }


        Map<String, String> cookies = new HashMap<>();
        cookies.put("ruid", "ugsAAOpdH102pdFvARQbAAB=");
        cookies.put("expires", "Thu, 31-Dec-37 23:55:55 GMT");
        cookies.put("domain", ".afisha.ru");
        cookies.put("path", "/msl_s=2");
        cookies.put("domain", ".afisha.ru");
        cookies.put("path", "/");
        cookies.put("samesite", "lax");

        Document googleHTMLdoc = Jsoup.connect("https://w.kassa.rambler.ru/")
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .cookies(cookies)
                .referrer("https://www.afisha.ru/msk/schedule_cinema_product/232355/")
                .get();
        System.out.println(googleHTMLdoc);
    }
}
