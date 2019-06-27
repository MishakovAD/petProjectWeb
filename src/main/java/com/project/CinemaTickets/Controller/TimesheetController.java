package com.project.CinemaTickets.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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
        response.setContentType("text/plain");

        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }
}
