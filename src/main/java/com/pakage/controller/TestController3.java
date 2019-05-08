package com.pakage.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Возвращает именно текстовую строку, а не страницу в заготовках. В отличии от двух предыдущих контроллеров.
 * Дело в @RestController
 */

@RestController
public class TestController3 {
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String getWelcome2(){
        return "welcome";
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public String getWelcome3(){
        return "welcome";
    }
}
