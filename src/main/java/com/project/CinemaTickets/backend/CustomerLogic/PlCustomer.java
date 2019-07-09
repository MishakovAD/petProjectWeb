package com.project.CinemaTickets.backend.CustomerLogic;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
import java.util.*;

public class PlCustomer implements PliCustomer {
    public static void main(String[] args) throws IOException {
        long time = 1562342004814L;
        System.out.println(new Date(time));
        PlCustomer p = new PlCustomer();
        p.selenium();

    }


    public void selenium() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/User/AppData/Local/Google/Chrome/Application/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://afisha.yandex.ru/moscow/cinema/places/piat-zviozd-na-paveletskoi?place-schedule-preset=today");
        WebElement element = driver.findElement(By.cssSelector("yaticket.i-stat__click i-bem.yaticket_js_inited\""));
    }

    public void createRequestWithCookie() throws IOException {
        Document googleHTMLdoc = Jsoup.connect("https://widget.afisha.yandex.ru/w/sessions/MjM1fDE1MTIzMHwyOTQ4fDE1NjIzNjA0MDAwMDA%3D?widgetName=w25&clientKey=bb40c7f4-11ee-4f00-9804-18ee56565c87&lang=ru")
                //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                //.referrer("https://www.afisha.ru/movie/246135/")
                .get();
        System.out.println(googleHTMLdoc);
    }


    public void createRequestWithCookieFirst() throws IOException {
        try{
        HttpURLConnection connection = (HttpURLConnection) new URL("https://afisha.yandex.ru/moscow/cinema/places/piat-zviozd-na-paveletskoi?place-schedule-preset=today").openConnection();
//        List<String> coockies = conn.getHeaderFields().get("Set-Cookie");
//        for (String coockie : coockies) {
//            System.out.println(coockie);
//        }
//        conn.setRequestProperty("referer", "https://www.afisha.ru/msk/schedule_cinema_product/232355/");
//        conn.setRequestProperty("cookie", "ruid=ugsAAOpdH102pdFvARQbAAB=; expires=Thu, 31-Dec-37 23:55:55 GMT; domain=.afisha.ru; path=/msl_s=2; domain=.afisha.ru; path=/; samesite=lax");




//        Map<String, String> cookies = new HashMap<>();
//        cookies.put("ruid", "1CIAAAzxzlwxWSNkAbcuHQB=");
//        cookies.put("uuts", "4vrJyHTNnpJ8MYTgqinJDUYe6d6A9a63");
//        cookies.put("_gcl_au", "1.1.70808908.1562271532");
//        cookies.put("_ym_uid", "1562271542582649076");
//        cookies.put("_ym_d", "1562271542");
//        cookies.put("top100_id", "t1.2553558.428465130.1562271552394");
//        cookies.put("uxs_uid", "fcd2ecd0-9e98-11e9-8a34-410b32664a33");
//        cookies.put("_ym_wasSynced", "%7B%22time%22%3A1562271627491%2C%22params%22%3A%7B%22eu%22%3A0%7D%2C%22bkParams%22%3A%7B%7D%7D");
//        cookies.put("dvr", "1");
//        cookies.put("_ym_isad", "w");
//        cookies.put("_ym_visorc_27744069", "w");
//        cookies.put("_ym_visorc_11431643", "198612457.1852676079.1562271542.1562271542.1562351989.2");
//        cookies.put("__utma", "198612457");
//        cookies.put("__utmc", "198612457");
//        cookies.put("__utmz", "198612457.1562351989.2.2.utmcsr=afisha.ru|utmccn=(referral)|utmcmd=referral|utmcct=/movie/246135/");
//        cookies.put("_ym_visorc_23429449", "w");
//        cookies.put("__utmt_UA-2512196-1", "1");
//        cookies.put("Percent", "e2VmNWExNDRmLTEzNzgtNDMwMS1hYzk2LWYyZTY3NjM0MTBkOX18MjAyMC0wNS0wMSAwOTo1MzoxNA==");
//        cookies.put("_gk", "GA1.2.92754613.1562086227");
//        cookies.put("_gk_gid", "GA1.2.1097864792.1562086227");
//        cookies.put("_dc_gtm_UA-8038853-16", "1");
//        cookies.put("__utmb", "198612457.4.10.1562351989");
//        cookies.put("last_visit", "1562342004814::1562352804814");
//        cookies.put("lvr", "1562352798");
//        Document googleHTMLdoc = Jsoup.connect("https://w.kassa.rambler.ru/event/46857698/406566b7-7fa0-444f-bbd0-f2bd05cd0785/undefined/")
//                .header(":authority","w.kassa.rambler.ru")
//                .header(":method","GET")
//                .header(":path","/event/46857698/406566b7-7fa0-444f-bbd0-f2bd05cd0785/undefined/")
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
//                .cookies(cookies)
//                .referrer("https://www.afisha.ru/movie/246135/")
//                .get();
//        System.out.println(googleHTMLdoc);

        connection.setRequestMethod("GET");
////        connection.setRequestProperty(":authority", "w.kassa.rambler.ru");
////        connection.setRequestProperty(":path","/event/46857698/406566b7-7fa0-444f-bbd0-f2bd05cd0785/undefined/");
////        connection.setRequestProperty(":scheme", "https");
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
        connection.setRequestProperty("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
//        connection.setRequestProperty("cookie", "ruid=1CIAAAzxzlwxWSNkAbcuHQB=; uuts=4vrJyHTNnpJ8MYTgqinJDUYe6d6A9a63; _gcl_au=1.1.70808908.1562271532; _ym_uid=1562271542582649076; _ym_d=1562271542; top100_id=t1.2553558.428465130.1562271552394; uxs_uid=fcd2ecd0-9e98-11e9-8a34-410b32664a33; _ym_wasSynced=%7B%22time%22%3A1562271627491%2C%22params%22%3A%7B%22eu%22%3A0%7D%2C%22bkParams%22%3A%7B%7D%7D; dvr=wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:1562351641; _ym_isad=1; _ym_visorc_27744069=w; _ym_visorc_11431643=w; __utma=198612457.1852676079.1562271542.1562271542.1562351989.2; __utmc=198612457; __utmz=198612457.1562351989.2.2.utmcsr=afisha.ru|utmccn=(referral)|utmcmd=referral|utmcct=/movie/246135/; _ym_visorc_23429449=w; Percent=e2VmNWExNDRmLTEzNzgtNDMwMS1hYzk2LWYyZTY3NjM0MTBkOX18MjAyMC0wNS0wMSAwOTo1MzoxNA==; _gk=GA1.2.92754613.1562086227; _gk_gid=GA1.2.1097864792.1562086227; top100rb=NzA4KzcwOQ==; lvr=1562353850; __utmt_UA-2512196-1=1; __utmb=198612457.6.10.1562351989; last_visit=1562343059003::1562353859003");
        connection.setRequestProperty("referer", "https://yandex.ru");
//        connection.setRequestProperty("upgrade-insecure-requests", "1");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 Chrome/75.0.3770.100 Safari/537.36");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

        //Send request
            String urlParameters = "";
        DataOutputStream wr = new DataOutputStream (
                connection.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.close();

        //Get Response
            InputStream stream = connection.getInputStream();
            BufferedInputStream in = new BufferedInputStream(stream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
//            int length = 0;
//            byte[] data = new byte[1024];
//            while ((length = in.read(data)) != -1) {
//                System.out.write(data);
//            }
            in.close();
    } catch (Exception e) {
        e.printStackTrace();
    }


    }
}
