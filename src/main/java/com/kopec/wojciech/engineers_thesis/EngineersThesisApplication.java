package com.kopec.wojciech.engineers_thesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class EngineersThesisApplication {
    private static final Logger logger = LoggerFactory.getLogger(EngineersThesisApplication.class);
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(EngineersThesisApplication.class, args);
        logger.info("HOLIDAY BNB: Application started");
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
