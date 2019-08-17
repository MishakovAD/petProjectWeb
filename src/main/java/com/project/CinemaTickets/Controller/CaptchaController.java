package com.project.CinemaTickets.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Controller
public class CaptchaController {
    private Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @GetMapping({"/enter_captha"})
    public String getEnterCaptchaPage() {
        logger.info("Start method getEnterCaptchaPage() at " + LocalDateTime.now());
        return "timesheet";
    }

    @RequestMapping("/get_captcha_question")
    public void getCaptchaQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Start method getCaptchaQuestion() at " + LocalDateTime.now());
        String city = request.getParameter("user_city");

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
}
