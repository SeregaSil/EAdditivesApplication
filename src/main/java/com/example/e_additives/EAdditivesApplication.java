package com.example.e_additives;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:/eadditives.properties"),
        @PropertySource("classpath:/application.properties")
})
public class EAdditivesApplication {
    public static void main(String[] args){
        SpringApplication.run(EAdditivesApplication.class, args);
    }

    @Value("#{${eadditive.type.name}}")
    private String[] columAndTAbleNames;

    @Bean("names")
    public String[] getColumAndTAbleNames() {
        return columAndTAbleNames;
    }

    @Value("${spring.mail.username}")
    private String email;

    @Bean("email")
    public String getEmail() {
        return email;
    }


}
