package com.project.controller.TestControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class AjaxTestController {
    @RequestMapping("/testAjax")
    public String helloWorld(Model model) {
        return "test/testAjax";
    }

    @RequestMapping("/userServlet")
    public void respAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName").trim();
        if(userName == null || "".equals(userName))
            userName = "Guest";

        String content = "Привет, " + userName;
        response.setContentType("text/plain");

        OutputStream outStream = response.getOutputStream();
        outStream.write(content.getBytes("UTF-8"));
        outStream.flush();
        outStream.close();
    }
}
