package com.project.CinemaTickets.Controller;

import com.project.CinemaTickets.backend.ServerLogic.Worker.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Controller
public class FillingDatabaseController {
    private Logger logger = LoggerFactory.getLogger(FillingDatabaseController.class);

    @GetMapping({"/enter_captha"})
    public String getEnterCaptchaPage() {
        logger.info("Start method getEnterCaptchaPage() at " + LocalDateTime.now());
        Thread thread = new Thread((Runnable) worker, "workerThread");
        System.out.println("Thread starting");
        thread.start();
        System.out.println(thread.getName());
        return "timesheet";
    }

    @RequestMapping("/get_captcha_question")
    public void getCaptchaQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getCaptchaQuestion() at " + LocalDateTime.now());


        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print("");
        writer.flush();
        writer.close();
        logger.info("End of method getCaptchaQuestion() at " + LocalDateTime.now());
    }

    @RequestMapping("/enter_answer_captcha")
    public void enterAnswerCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method enterAnswerCaptcha() at " + LocalDateTime.now());
        String city = request.getParameter("user_city");

        response.setContentType("application/json");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
        writer.print("");
        writer.flush();
        writer.close();
        logger.info("End of method enterAnswerCaptcha() at " + LocalDateTime.now());
    }

    //--------------------------Injections part ---------------------------
    private Worker worker;

    @Inject
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
