package com.generation.brain.smartparse01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:textFileHandler.properties")
public class SpringConfig {

    @Bean
    public TextFileHandler textFileHandler() {
        return new TextFileHandler();
    }

}