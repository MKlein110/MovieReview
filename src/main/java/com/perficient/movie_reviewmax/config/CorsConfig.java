//package com.perficient.movie_reviewmax.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
////@Configuration
//public class CorsConfig {
//	
//    //	@Bean
//    public WebMvcConfigurer corsConfigurer() {
//
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry
//                        .addMapping("/**")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*")
//                        .allowedOriginPatterns("*")
//                        .allowedOrigins("http://localhost:3000", "http://localhost:8080/moviereview/oauth2/authorization/google", "http://localhost:8080/moviereview/movies", "http://localhost:8080/moviereview/review");
//                
//            }
//        };
//    }
//}
