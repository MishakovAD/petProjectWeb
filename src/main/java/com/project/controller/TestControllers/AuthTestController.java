package com.project.controller.TestControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class AuthTestController {
    @RequestMapping("/testAuth")
    public String testAuth(Model model) {
        return "test/testAuth";
    }

    @RequestMapping("/testAuthResp")
    public void respAuthAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName").trim();
        String password = request.getParameter("password");
        if(userName == null || "".equals(userName))
            userName = "Guest";

        String content = "Привет, " + userName + password;
        response.setContentType("text/plain");

        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }
}
