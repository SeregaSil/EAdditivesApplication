package com.example.e_additives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/** Класс для запуска приложения. */
@SpringBootApplication
@PropertySource("classpath:/eadditives.properties")
public class EAdditivesApplication {
    public static void main(String[] args){
        SpringApplication.run(EAdditivesApplication.class, args);
    }
}
