package com.perficient.movie_reviewmax.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.perficient.movie_reviewmax.custom.exception.CustomAccessDeniedHandler;
import com.perficient.movie_reviewmax.security.CustomAuthenticationSuccessHandler;
import com.perficient.movie_reviewmax.service.CustomOidcUserService;

/*
 * First connection b/w initial web page and google 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomOidcUserService oidcUserService;

	@Autowired
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	// logger definition
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	/*
	 * Method: configure()
	 * 
	 * Parameters: http - allow web based security for specific http requests
	 * 
	 * Return: void
	 * 
	 * Description: cors configuration. Permits specified end points and provides
	 * proper authorization. Connects with Google's OAuth2 to log in user to
	 * application.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off

        http
        .cors().configurationSource(corsConfigurationSource()).and()
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**", "**/movies/**", "/movies/{id}", "/search", "/oauth2/authorization/google", "/deleteFilms/**").permitAll()
                .antMatchers("/review").authenticated()
            )
            //login for Google
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
            //get user attributes and redirect and generate token (once auth)
            .successHandler(authenticationSuccessHandler)
            .and()
           
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            //.csrf().disable()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
            //.defaultSuccessUrl("/movies");
        // @formatter:on
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	} // ... .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

	/*
	 * Method: corsConfigurationSource()
	 * 
	 * Parameters: none
	 * 
	 * Return: source - url path with cors config properties
	 * 
	 * Description: Set proper cors configuration properties to allow connection to
	 * origin, perform specified methods/action, and permit header type.
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		// cors configuration
		CorsConfiguration configuration = new CorsConfiguration();

		// set cors configuration properties
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080", "null"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));

		// option type header (allow content type connection)
		configuration.setAllowedHeaders(Arrays.asList("content-type", "authorization", "X-CSRF-TOKEN"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
	



}
