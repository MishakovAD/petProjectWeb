package com.pakage.controller.MainPage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контроллер, обрабатывающий запросы на главной странице.
 */

@RestController
public class MainPageController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage() {
        return new ModelAndView("pages/index");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getWelcome2(){
        return "welcome";
    }
}
