package com.project.CinemaTickets.backend.ServerLogic.Worker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.net.URLConnection;

@Component
public class WorkerImpl extends Thread implements Worker {
    private boolean running = true;

    public void run() {
        while (isRunning()) {
            System.out.println("Worker run");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public String start(StringBuilder capthaDocument) {
        StringBuilder url = new StringBuilder("https://www.kinopoisk.ru/checkcaptcha?key=");
        Document captchaDoc = Jsoup.parse(capthaDocument.toString());
        String key = captchaDoc.getElementsByAttributeValue("name", "key").attr("value");
        String retpath = captchaDoc.getElementsByAttributeValue("name", "retpath").attr("value");
        String srcImg = captchaDoc.getElementsByAttributeValue("class", "image form__captcha").attr("src");



        return url.toString();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static void main(String[] args) {
        WorkerImpl w = new WorkerImpl();
        StringBuilder sb = new StringBuilder();
        w.start(sb);
    }
}
