package com.project.CinemaTickets.Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

@Controller
public class TimesheetController {
    @GetMapping({"/cinema"})
    public String getTimesheetPage() {
        return "timesheet";
    }

    @RequestMapping("/timesheetquery")
    public void respTimesheet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String timesheetquery = request.getParameter("timesheetquery").trim();

        String content = "timesheetquery: " + timesheetquery;
        parser(timesheetquery);
        response.setContentType("text/plain");

        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }

    public void getData(String query) throws IOException {
            int c;
            URL myUrl = new URL(query);
            URLConnection myUrlCon = myUrl.openConnection();

            // Получить дату
            long d = myUrlCon.getDate();
            if(d == 0)
                System.out.println("Сведения о дате отсутствуют.");
            else
                System.out.println("Дата: " + new Date(d));

            // Получить тип содержимого
            System.out.println("Типа содержимого: "
                    + myUrlCon.getContentType());
            // Получить длину содержимого
            long length = myUrlCon.getContentLengthLong();
            if(length == -1)
                System.out.println("Длина содержимого недоступна");
            else
                System.out.println("Длина содержимого: " + length);

            if(length != 0) {
                System.out.println("=== Содержимое ===");
                String line;
                InputStream input = myUrlCon.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                input.close();
                reader.close();
            } else {
                System.out.println("Содержимое недоступно.");
            }
    }


    /**
     * Метод, который позволяет распарсить HTML страницу и получить из нее список всех кинотеатров, сеансов, адресов, стоимостью, рейтингом
     * для перемещения по страницам (а не "загрузить больше") использовать ссылку вида: https://www.afisha.ru/msk/schedule_cinema_product/232355/page2/?utm_content=afishapv
     * где меняется только параметр страницы
     * и проверять все страницы до тех пор, пока не придет ответ с 404 ошибкой.
     * @param query
     * @throws IOException
     */
    public void parser(String query) throws IOException {
        Document HTMLdoc = Jsoup.connect(query).get();
        System.out.println(HTMLdoc);
    }
}
