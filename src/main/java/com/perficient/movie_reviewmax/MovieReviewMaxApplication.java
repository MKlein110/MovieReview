package com.perficient.movie_reviewmax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
public class MovieReviewMaxApplication extends WebSecurityConfigurerAdapter {
	
	public static void main(String[] args) {
		SpringApplication.run(MovieReviewMaxApplication.class, args);
	}


	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off

        http
        .cors().and()
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**", "/movies", "/movies/{id}", "/search").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login()
            .defaultSuccessUrl("/movies");
        // @formatter:on
    }

}
