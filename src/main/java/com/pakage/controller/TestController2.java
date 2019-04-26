package com.pakage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/welcome")
public class TestController2 {

    @RequestMapping(method = RequestMethod.GET)
    public String getWelcome(){
        return "welcome";
    }
}
