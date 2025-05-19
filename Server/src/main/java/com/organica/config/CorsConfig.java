package com.organica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO Auto-generated method stub
                registry.addMapping("/**")
            .allowedOrigins("http://34.118.238.107", "http://34.56.70.220/")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);

        WebMvcConfigurer.super.addCorsMappings(registry);
    }
    
}
