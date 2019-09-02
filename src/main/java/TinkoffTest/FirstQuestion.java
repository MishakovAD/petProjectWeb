package TinkoffTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Не все банки похожи на Тинькофф: большинство работают по старой схеме –
 * открывают отделения и почти все операции проводят там. В городе N открылось
 * круглосуточное отделение "Финансы Энска" с тремя окнами. Каждое из окон обслуживает
 * любого клиента ровно полчаса. Как только окно освобождается, в него тут же переходит
 * следующий клиент из очереди. Если клиентов нет, консультанты отдыхают до прихода нового
 * клиента. Найдите, во сколько каждый клиент сможет выйти из отделения
 *
 * Входные данные
 * Первая строка содержит натуральное число N (N <= 100) – количество клиентов.
 * Далее в N строк содержат времена прихода клиентов – по два числа, обозначающие
 * часы и минуты (часы – от 0 до 23, минуты – от 0 до 59). Времена указаны в порядке
 * возрастания и различны. Гарантируется, что всех клиентов успеют обслужить до полуночи.
 * Результат работы
 * Выведите N пар чисел: часы и минуты выхода из отделения 1-го, 2-го, …, N-го клиента.
 */
public class FirstQuestion {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer counter = null;
        while (counter == null) {
            System.out.println("Введите количество клиентов (цифрой): ");
            try {
                counter = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException ex) {
                System.out.println("Данные некоректны, попробуйте снова.");
                counter = null;
            }

        }
        List<LocalTime> timeList = new LinkedList<>();
        for (int i = 0; i < counter; i++) {
            String time = null;
            while (time == null) {
                System.out.println("Введите через проблел часы и минуты для " + counter + " пользователей: ");
                try {
                    time = reader.readLine();
                    String hour = time.substring(0, time.indexOf(" "));
                    String minute = time.replaceAll(hour + " ", "");
                    LocalTime t = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
                    timeList.add(t);
                } catch (Exception ex) {
                    System.out.println("Данные некоректны, попробуйте снова.");
                    time = null;
                }
            }
        }

        timeList.forEach(time -> {
            if (time.getHour() == 23 && time.getMinute() > 30) {
                System.out.println("23:59"); //т.к. все клиенты должны быть обслужаны до полуночи
            } else {
                System.out.println(time.plusMinutes(30));
            }
        });

    }
}
