package com.perficient.movie_reviewmax.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.perficient.movie_reviewmax.repo.UserRepository;

@EnableWebSecurity
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserRepository userRepo;
	
	@Override@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	public WebConfig(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticator) throws Exception {
		System.out.println("Find by email");
		authenticator.userDetailsService(userEmail -> userRepo
				.findByEmail(userEmail));
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off

        http
        .cors().configurationSource(corsConfigurationSource()).and()
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**", "**/movies/**", "/movies/{id}", "/search").permitAll()
                .antMatchers("/review").authenticated()
            )
            .oauth2Login();
            //.defaultSuccessUrl("/movies");
        // @formatter:on
    }
	
	   @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
	        configuration.setAllowedHeaders(Arrays.asList("content-type"));
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
}
