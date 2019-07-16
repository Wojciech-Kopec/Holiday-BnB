package com.kopec.wojciech.enginners_thesis;

import com.google.common.base.Strings;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.rest.AppInfoRestController;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;


@SpringBootApplication
public class EnginnersThesisApplication {
    private static final Logger logger = LoggerFactory.getLogger(EnginnersThesisApplication.class);
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(EnginnersThesisApplication.class, args);
        logger.info("HOLIDAY BNB: Application started");
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
