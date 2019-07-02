package com.pakage.backend.Internet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Данный класс задумывается, как перехватчик трафика от приложений, чтобы
 * была возможность перехватить трафик видео, записать его локально
 * и затем отправить
 * Из разряда: пользователь вводит ссылку с видео - устанавливается соединение - видео приходит в пакетах, которые перехватываются -
 * - записывается локально - видео отправляется
 */

public class StreamYoutube {
    public static void main(String[] args) throws IOException {
        URL hp = new URL("http://www.youtube.com/watch?v=emVCC4ttJnw");

        System.out.println("Protocol: " + hp.getProtocol());

        System.out.println("Port: " + hp.getPort());

        System.out.println("Host: " + hp.getHost());

        System.out.println("File: " + hp.getFile());

        int c;

        URLConnection hpCon = hp.openConnection();

        System.out.println("Date: " + hpCon.getDate());

        System.out.println("Type: " + hpCon.getContentType());

        System.out.println("Exp: " + hpCon.getExpiration());

        System.out.println("Last M: " + hpCon.getLastModified());

        System.out.println("Length: " + hpCon.getContentLength());

        if (hpCon.getContentLength() > 0) {

            System.out.println("=== Content ===");

            InputStream input = hpCon.getInputStream();

            int i = hpCon.getContentLength();

            while (((c = input.read()) != -1) && (--i > 0)) {

                System.out.print((char) c);

            }

            input.close();

        }
    }
}
