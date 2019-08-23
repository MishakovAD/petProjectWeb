package com.project.RecognitionImage;

import com.project.CinemaTickets.ApplicationCinemaTickets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Данный проект предназначается для распознавания изображений любых форматов.
 */
@SpringBootApplication
public class ApplicatonRecognitionImage extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationCinemaTickets.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationCinemaTickets.class, args);
    }
}
