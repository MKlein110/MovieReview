package com.perficient.movie_reviewmax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
public class MovieReviewMaxApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MovieReviewMaxApplication.class, args);
	}




}
