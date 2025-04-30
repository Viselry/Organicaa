package com.organica;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableCaching
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.organica.repositories")
@EntityScan(basePackages = "com.organica.entities")
@EnableConfigurationProperties(KafkaProperties.class)
public class OrganicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganicaApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}



	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}
}
