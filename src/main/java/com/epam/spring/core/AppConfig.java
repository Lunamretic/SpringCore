package com.epam.spring.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.util.Date;

@Configuration
public class AppConfig {
    @Bean
    public Date date() {
        return new Date();
    }

    @Bean
    public DateFormat dateFormat() {
        return DateFormat.getDateTimeInstance();
    }
}
