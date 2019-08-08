package com.project.CinemaTickets.backend.Utils;

import java.util.Random;
import java.util.UUID;

public class HelperUtils {

    /**
     * Генерация уникальной неповторяющейся(надеюсь) строки.
     * @return уникальная строка
     */
    public static String createUniqueID() {
        return UUID.randomUUID().toString()
                .substring(0, 13).replaceAll("-", String.valueOf(new Random(System.currentTimeMillis()).nextInt(777)));
    }
}
