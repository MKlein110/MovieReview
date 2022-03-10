package com.perficient.movie_reviewmax.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.perficient.movie_reviewmax.security.CustomAuthenticationSuccessHandler;
import com.perficient.movie_reviewmax.service.CustomOidcUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomOidcUserService oidcUserService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off

        http
        .cors().configurationSource(corsConfigurationSource()).and()
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**", "**/movies/**", "/movies/{id}", "/search").permitAll()
                .antMatchers("/review").authenticated()
            )
            .oauth2Login()
            .redirectionEndpoint()
//            .baseUri("/oauth2/callback/google")
            .and()
            .userInfoEndpoint()
            .oidcUserService(oidcUserService)
            .and()
            .authorizationEndpoint()
            .authorizationRequestRepository(customAuthorizationRequestRepository())
            .and()
            .successHandler(authenticationSuccessHandler)
            .and()
            .csrf().disable();
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
	   
	   
	   @Bean
	   public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
	   	return new HttpSessionOAuth2AuthorizationRequestRepository();
	   }
}