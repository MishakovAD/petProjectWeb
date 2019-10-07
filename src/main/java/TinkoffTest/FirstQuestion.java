package TinkoffTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FirstQuestion implements Comparable {
    int mT;
    int edmT;

    public static void main(String[] args) throws IOException {
        String str1 = "abc";
        String str2 = "cba";
        str1.concat(str2);
        System.out.println(str1);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        Integer counter = null;
//        while (counter == null) {
//            try {
//                counter = Integer.parseInt(reader.readLine());
//            } catch (NumberFormatException ex) {
//                counter = null;
//            }
//
//        }
//        List<LocalTime> timeList = new LinkedList<>();
//        for (int i = 0; i < counter; i++) {
//            String time = null;
//            while (time == null) {
//                try {
//                    time = reader.readLine();
//                    String hour = time.substring(0, time.indexOf(" "));
//                    String minute = time.replaceAll(hour + " ", "");
//                    LocalTime t = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
//                    timeList.add(t);
//                } catch (Exception ex) {
//                    time = null;
//                }
//            }
//        }
//        reader.close();
//
//        timeList.forEach(time -> {
//            if (time.getHour() == 23 && time.getMinute() > 30) {
//                System.out.println("23:59"); //т.к. все клиенты должны быть обслужаны до полуночи
//            } else {
//                time = time.plusMinutes(30);
//                System.out.println(time.getHour() + " " + time.getMinute());
//            }
//        });

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    static class Test {
        public static void m() {

        }
    }
}
