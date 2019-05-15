package com.pakage.controller.MainPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер, обрабатывающий запросы на главной странице.
 */

@Controller
public class MainPageController {
    @GetMapping({"/", "/hello"})
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name, HttpServletResponse response) {
        name = "Мир";
        response.setContentType("text/plain;charset=UTF-8");
        model.addAttribute("name", name);
        return "index";
    }
}
