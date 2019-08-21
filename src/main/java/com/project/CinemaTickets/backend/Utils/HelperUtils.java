package com.project.CinemaTickets.backend.Utils;

import com.project.CinemaTickets.backend.ServerLogic.DAO.Entity.Cinema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HelperUtils {

    /**
     * Генерация уникальной неповторяющейся(надеюсь) строки.
     *
     * @return уникальная строка
     */
    public static String createUniqueID() {
        return UUID.randomUUID().toString()
                .substring(0, 13).replaceAll("-", String.valueOf(new Random(System.currentTimeMillis()).nextInt(777)));
    }

    public static ArrayList<String> createIdCinemas() {
        ArrayList<String> idList = new ArrayList<>();
        for (int i = 280268; i < 281250; i++) {
            String generateId = String.valueOf(i);
            idList.add(generateId);
        }
        return idList;
    }

    public static LocalDate[] initWeekArray() {
        LocalDate[] dateArray = new LocalDate[7];
        for (int i = 0; i < 7; i++) {
            dateArray[i] = LocalDate.now().plusDays(i);
        }
        return dateArray;
    }
}
