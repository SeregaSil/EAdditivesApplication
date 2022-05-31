package com.example.e_additives.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/css/**", "/static/js/**", "/static/img/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/js/", "classpath:/static/img/");
    }
}
