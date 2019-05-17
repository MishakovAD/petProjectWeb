package com.pakage.controller.TestControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BootstrapTestControllers {
    @RequestMapping("/testBootstrap")
    public String helloWorld(Model model) {
        return "test/testBootstrap";
    }
}
