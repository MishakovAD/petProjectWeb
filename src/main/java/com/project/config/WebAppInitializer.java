package com.project.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        //ctx.register(WebMvcConfigure.class); //Когда конфигурация - один класс
        context.setConfigLocation("com.project.config"); //Сканирует пакет на наличие конфигов
        context.setServletContext(container);

        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic MainPageservlet = container.addServlet(
                "MainPage", new DispatcherServlet(context));
        MainPageservlet.setLoadOnStartup(1);
        MainPageservlet.addMapping("/");
    }
}
